package com.dataworks.eventsubscriber.service.event;

import com.dataworks.eventsubscriber.exception.category.CategoryNotFoundException;
import com.dataworks.eventsubscriber.exception.event.EventNotFoundException;
import com.dataworks.eventsubscriber.exception.event.EventUserAlreadySubscribedException;
import com.dataworks.eventsubscriber.mapper.AnswerMapper;
import com.dataworks.eventsubscriber.mapper.EventMapper;
import com.dataworks.eventsubscriber.mapper.QuestionMapper;
import com.dataworks.eventsubscriber.mapper.UserMapper;
import com.dataworks.eventsubscriber.model.dao.Answer;
import com.dataworks.eventsubscriber.model.dao.Event;
import com.dataworks.eventsubscriber.model.dao.User;
import com.dataworks.eventsubscriber.model.dto.AnswerDto;
import com.dataworks.eventsubscriber.model.dto.EventDto;
import com.dataworks.eventsubscriber.repository.CategoryRepository;
import com.dataworks.eventsubscriber.repository.EventRepository;
import com.dataworks.eventsubscriber.service.auth.AuthService;
import com.dataworks.eventsubscriber.service.storage.LocalStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {
    private final AuthService authService;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserMapper userMapper;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;
    private final LocalStorageService localStorageService;
    private final CategoryRepository categoryRepository;

    @Override
    public EventDto store(EventDto eventDto) {
        var loggedInUser = authService.myDaoOrFail();
        var mappedEvent = eventMapper.mapToEventSource(eventDto);

        var category = categoryRepository.findById(eventDto.getCategory().getId())
                .orElseThrow(CategoryNotFoundException::new);

        mappedEvent.setUser(loggedInUser);
        mappedEvent.setCategory(category);

        var savedEvent = eventRepository.save(mappedEvent);

        return eventMapper.mapToEventDestination(savedEvent);
    }

    @Override
    public EventDto update(int id, EventDto eventDto) {
        User loggedInUser = authService.myDaoOrFail();
        Optional<Event> eventFromRepo = loggedInUser.isAdmin() ?
                eventRepository.findById(id) :
                eventRepository.findByIdAndUser_Id(id, loggedInUser.getId());

        if (eventFromRepo.isEmpty()) {
            throw new EventNotFoundException();
        }

        var category = categoryRepository.findById(eventDto.getCategory().getId())
                .orElseThrow(CategoryNotFoundException::new);

        Event ev = eventFromRepo.get();
        ev.setDate(eventDto.getDate());
        ev.setDescription(eventDto.getDescription());
        ev.setEuroAmount(eventDto.getEuroAmount());
        ev.setMaxAmountOfAttendees(eventDto.getMaxAmountOfAttendees());
        ev.setTitle(eventDto.getTitle());
        ev.setImageUrl(eventDto.getImageUrl());
        ev.setCategory(category);

        return eventMapper.mapToEventDestination(eventRepository.save(ev));
    }

    @Override
    public List<EventDto> findAll() {
        return eventRepository.findAll(Sort.by("date").descending())
                .stream()
                .map(eventMapper::mapToEventDestination)
                .collect(Collectors.toList());
    }

    @Override
    public EventDto findById(int id) {
        var event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
        var mappedEvent = eventMapper.mapToEventDestination(event);
        var mappedQuestions = event.getQuestions().stream().map((question) -> {
            var mappedQuestion = questionMapper.mapToDestination(question);
            List<AnswerDto> filteredSuperiorAnswers = question.getAnswers().stream()
                    .filter(answer -> {
                        var eventOwner = event.getUser().getId();
                        var answerOwner = answer.getOwner().getId();
                        var answerOfAdmin = answer.getOwner().isAdmin();

                        return eventOwner.equals(answerOwner) || answerOfAdmin;
                    })
                    .map(answerMapper::mapToEventDestination)
                    .collect(Collectors.toList());

            List<AnswerDto> filteredUserAnswers = question.getAnswers().stream()
                    .filter(answer -> {
                        var eventOwner = event.getUser().getId();
                        var answerOwner = answer.getOwner().getId();
                        var answerOfAdmin = answer.getOwner().isAdmin();

                        return !eventOwner.equals(answerOwner) && !answerOfAdmin;
                    })
                    .map(answerMapper::mapToEventDestination)
                    .collect(Collectors.toList());;

            mappedQuestion.setSuperiorAnswers(filteredSuperiorAnswers);
            mappedQuestion.setUserAnswers(filteredUserAnswers);

            return mappedQuestion;
        }).collect(Collectors.toList());

        mappedEvent.setQuestions(mappedQuestions);

        return mappedEvent;
    }

    @Override
    public EventDto subscribe(int id) {
        var loggedInUser = authService.myDaoOrFail();
        var event = eventRepository.findById(id).orElseThrow(() -> new EventNotFoundException(id));
        var isUserSubscribed = eventRepository.findByIdAndSubscribedUsers_Id(id, loggedInUser.getId());

        if (isUserSubscribed.isPresent()) {
            throw new EventUserAlreadySubscribedException();
        }

        event.getSubscribedUsers().add(loggedInUser);
        var savedEvent = eventRepository.save(event);
        return findById(savedEvent.getId());
    }

    @Override
    public List<EventDto> findByUserId() {
        var loggedInUser = authService.myDaoOrFail();
        return eventRepository.findByUserId(loggedInUser.getId())
                .stream()
                .map(eventMapper::mapToEventDestination)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDto> findBySubscribedUsers() {
        var loggedInUser = authService.myDaoOrFail();
        return eventRepository.findBySubscribedUsers_Id(loggedInUser.getId())
                .stream()
                .map(eventMapper::mapToEventDestination)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(int eventId) {
        var event = eventRepository.findById(eventId).orElseThrow(EventNotFoundException::new);

        var isOrganizer = authService.myDao().getId().equals(event.getUser().getId());
        if (authService.myDao().isAdmin() || isOrganizer) {
            eventRepository.deleteById(eventId);
        } else {
            throw new EventNotFoundException();
        }
    }
}
