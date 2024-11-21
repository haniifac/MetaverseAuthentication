package org.ukdw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.ukdw.authservice.entity.GroupEntity;
import org.ukdw.authservice.repository.GroupRepository;

@Component
public class PopulateDatabase implements CommandLineRunner {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if groups already exist, if not, insert them
        if (groupRepository.count() == 0) {
            // Create and save GROUP records
            GroupEntity studentGroup = new GroupEntity();
            studentGroup.setGroupname("STUDENT");
            studentGroup.setPermission(1L);  // Set the appropriate permission bit value
            groupRepository.save(studentGroup);

            GroupEntity teacherGroup = new GroupEntity();
            teacherGroup.setGroupname("TEACHER");
            teacherGroup.setPermission(3L);  // Set the appropriate permission bit value
            groupRepository.save(teacherGroup);

            GroupEntity adminGroup = new GroupEntity();
            adminGroup.setGroupname("ADMIN");
            adminGroup.setPermission(511L);  // Set the appropriate permission bit value
            groupRepository.save(adminGroup);

            System.out.println("Default groups inserted successfully.");
        } else {
            System.out.println("Groups already exist, skipping insert.");
        }
    }
}
