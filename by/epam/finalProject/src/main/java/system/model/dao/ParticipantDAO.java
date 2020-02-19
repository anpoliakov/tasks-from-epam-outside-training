package system.model.dao;

import system.model.entity.Human;
import system.model.entity.Participant;


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
