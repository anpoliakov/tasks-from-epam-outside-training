package by.epam.task6.model.dao;

import by.epam.task6.model.entity.Human;
import by.epam.task6.model.entity.Participant;

/**
 *
 *  Transitional interface to which individual methods can be added
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public interface ParticipantDAO extends GenericDAO <Participant> {
    //search for participants
    Participant find(Human fromHuman, Human forHuman);

    //search for owner-forester communication in which forester will = human
    Participant findHumanForester(Human human);
}
