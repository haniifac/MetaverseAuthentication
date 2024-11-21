package org.ukdw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ukdw.authservice.entity.GroupEntity;
import org.ukdw.authservice.entity.UserAccountEntity;
import org.ukdw.authservice.repository.GroupRepository;
import org.ukdw.authservice.repository.UserAccountRepository;

import java.util.Set;
import java.util.List;

@Component
public class PopulateDatabase implements CommandLineRunner {
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Insert group data first
        if (groupRepository.count() == 0) {
            createInitialGroups();
            System.out.println("Default groups inserted successfully.");
        } else {
            System.out.println("Groups already exist, skipping insert.");
        }

        // Insert user data with associated group entities
        if (userAccountRepository.count() == 0) {
            createInitialUsers();
            System.out.println("Default users inserted successfully.");
        } else {
            System.out.println("Users already exist, skipping insert.");
        }
    }

    private void createInitialGroups() {
        // Create and save GROUP records
        GroupEntity studentGroup = new GroupEntity();
        studentGroup.setGroupname("STUDENT");
        studentGroup.setPermission(1L);
        groupRepository.save(studentGroup);

        GroupEntity teacherGroup = new GroupEntity();
        teacherGroup.setGroupname("TEACHER");
        teacherGroup.setPermission(3L);
        groupRepository.save(teacherGroup);

        GroupEntity adminGroup = new GroupEntity();
        adminGroup.setGroupname("ADMIN");
        adminGroup.setPermission(511L);
        groupRepository.save(adminGroup);
    }

    private void createInitialUsers() {
        // Retrieve groups
        GroupEntity studentGroup = groupRepository.findByGroupname("STUDENT");
        GroupEntity teacherGroup = groupRepository.findByGroupname("TEACHER");
        GroupEntity adminGroup = groupRepository.findByGroupname("ADMIN");

        // Sample user data
        UserAccountEntity newUser2 = new UserAccountEntity(
                "admin@example.com",
                "admin",
                "password",
                "REG124",
                "admin"
        );
        newUser2.setGroups(Set.of(adminGroup));

        UserAccountEntity newUser3 = new UserAccountEntity(
                "teacher@example.com",
                "teacher",
                "password",
                "REG125",
                "teacher"
        );
        newUser3.setGroups(Set.of(teacherGroup));

        UserAccountEntity teacher = new UserAccountEntity(
                "dendy.prtha@staff.ukdw.ac.id",
                "dendy",
                "qwe123",
                "TCR001",
                "teacher"
        );
        teacher.setGroups(Set.of(teacherGroup));

        UserAccountEntity student = new UserAccountEntity(
                "prtha@student.ukdw.ac.id",
                "prtha",
                "qwe123",
                "STD001",
                "student"
        );
        student.setGroups(Set.of(studentGroup));

        // Save all users to the database in one save
        userAccountRepository.saveAll(List.of(newUser2, newUser3, teacher, student));
    }
}