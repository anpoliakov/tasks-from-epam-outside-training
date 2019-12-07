package by.epam.task4.startProgram;

import by.epam.task4.model.dao.*;
import by.epam.task4.model.entity.*;
import by.epam.task4.model.factories.DAOFactory;

import java.util.List;

/**
 *
 *    !!!! File can be deleted - created for test !!!
 *
 *
 *    WARNING :
 *    There is a mistake - the system is built taking into account the fact that
 *    work is performed with entities from the database, so you must first dump the entity into
 *    the database, and then get and use it in other places (for the presence of id)
 *
 *
 *    I will make the task better - if necessary !
 *
 *
 * **/
public class Runner {
    public static void main(String[] args) {
        //get mySQL factory
        DAOFactory mySQLFactory = DAOFactory.getFactory();

        //variables declaration
        Plant plant1,plant2;
        Role role1, role2;
        Action action1, action2;
        Human human1, human2;

        //classes for working with the database
        PlantDAO plantDAOImpl = mySQLFactory.createPlantDAOImpl();
        ActionDAO actionDAOImpl = mySQLFactory.createActionDAOImpl();
        HumanDAO humanDAOImpl = mySQLFactory.createHumanDAOImpl();
        RoleDAO roleDAOImpl = mySQLFactory.createRoleDAOImpl();

        ParticipantDAO participantDAOImpl = mySQLFactory.createParticipantDAOImpl();
        MissionDAO missionDAOImpl = mySQLFactory.createMissionDAOImpl();

        plant1 = plantDAOImpl.find("Oak");
        plant2 = plantDAOImpl.find("Red Rose");

        role1 = roleDAOImpl.find("Owner");
        role2 = roleDAOImpl.find("forester");

        action1 = actionDAOImpl.find("Planting");
        action2 = actionDAOImpl.find("Art processing");

        humanDAOImpl.create(new Human("Andrew", "Paliakov", role1 ));
        humanDAOImpl.create(new Human("Melano", "Bobrero", role2 ));
        human1 = humanDAOImpl.find("Andrew","Paliakov");
        human2 = humanDAOImpl.find("Melano","Bobrero");

        participantDAOImpl.create(new Participant(human1,human2));
        Participant participant = participantDAOImpl.find(human1,human2);

        Mission mission1 = new Mission(participant,action2,plant2,false);
        int i = missionDAOImpl.create(mission1);

        //Task execution and report
        System.out.println("human2 id = " + human2.getId());
        List<Mission> missionHuman = missionDAOImpl.findMissionHuman(human2);

        //perform all tasks entrusted by human2
        for (Mission m : missionHuman){
            boolean flag = false;
            if(missionDAOImpl.makeMission(m)){
                System.out.println("Mission - \"" + m.getAction().getName() + "\" mission successfully completed !");
            }
        }
    }
}
