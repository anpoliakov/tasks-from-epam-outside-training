package by.epam.task6.model.dao;

import by.epam.task6.model.entity.*;

import java.util.List;

/**
 *
 *  Transitional interface to which individual methods can be added
 *
 * @author anpoliakov
 * @version 1.0.0
 *
 * */
public interface MissionDAO extends GenericDAO <Mission> {
    //unique methods for the class MissionDAOImpl

    //This method accurately determines the mission by 3 parameters
    Mission find(Participant participant, Action action, Plant plant);

    //carry out the task
    boolean makeMission(Mission mission);

    //looking for all the tasks for this human
    List<Mission> findMissionHuman(Human human);
}
