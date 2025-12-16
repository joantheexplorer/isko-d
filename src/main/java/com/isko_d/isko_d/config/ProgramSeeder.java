package com.isko_d.isko_d.config;

import com.isko_d.isko_d.model.Department;
import com.isko_d.isko_d.model.Program;
import com.isko_d.isko_d.repository.DepartmentRepository;
import com.isko_d.isko_d.repository.ProgramRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class ProgramSeeder {

    @Bean
    CommandLineRunner seedPrograms(ProgramRepository programRepo, DepartmentRepository deptRepo) {
        return args -> {
            if (programRepo.count() == 0) {
                List<Program> programs = new ArrayList<>();

                deptRepo.findByName("College of Accountancy and Finance (CAF)").ifPresent(d -> {
                    programs.add(new Program("Bachelor of Science in Accountancy (BSA)", d));
                    programs.add(new Program("Bachelor of Science in Management Accounting (BSMA)", d));
                    programs.add(new Program("Bachelor of Science in Business Administration Major in Financial Management (BSBAFM)", d));
                });

                deptRepo.findByName("College of Architecture, Design and the Built Environment (CADBE)").ifPresent(d -> {
                    programs.add(new Program("Bachelor of Science in Architecture (BS-ARCH)", d));
                    programs.add(new Program("Bachelor of Science in Interior Design (BSID)", d));
                    programs.add(new Program("Bachelor of Science in Environmental Planning (BSEP)", d));
                });

                deptRepo.findByName("College of Arts and Letters (CAL)").ifPresent(d -> {
                    programs.add(new Program("Bachelor of Arts in English Language Studies (ABELS)", d));
                    programs.add(new Program("Bachelor of Arts in Filipinology (ABF)", d));
                    programs.add(new Program("Bachelor of Arts in Literary and Cultural Studies (ABLCS)", d));
                    programs.add(new Program("Bachelor of Arts in Philosophy (AB-PHILO)", d));
                    programs.add(new Program("Bachelor of Performing Arts major in Theater Arts (BPEA)", d));
                });

                deptRepo.findByName("College of Business Administration (CBA)").ifPresent(d -> {
                    programs.add(new Program("Doctor in Business Administration (DBA)", d));
                    programs.add(new Program("Master in Business Administration (MBA)", d));
                    programs.add(new Program("Bachelor of Science in Business Administration major in Human Resource Management (BSBAHRM)", d));
                    programs.add(new Program("Bachelor of Science in Business Administration major in Marketing Management (BSBA-MM)", d));
                    programs.add(new Program("Bachelor of Science in Entrepreneurship (BSENTREP)", d));
                    programs.add(new Program("Bachelor of Science in Office Administration (BSOA)", d));
                });

                deptRepo.findByName("College of Communication (COC)").ifPresent(d -> {
                    programs.add(new Program("Bachelor in Advertising and Public Relations (BADPR)", d));
                    programs.add(new Program("Bachelor of Arts in Broadcasting (BA Broadcasting)", d));
                    programs.add(new Program("Bachelor of Arts in Communication Research (BACR)", d));
                    programs.add(new Program("Bachelor of Arts in Journalism (BAJ)", d));
                });

                deptRepo.findByName("College of Computer and Information Sciences (CCIS)").ifPresent(d -> {
                    programs.add(new Program("Bachelor of Science in Computer Science (BSCS)", d));
                    programs.add(new Program("Bachelor of Science in Information Technology (BSIT)", d));
                });

                deptRepo.findByName("College of Education (COED)").ifPresent(d -> {
                    programs.add(new Program("Bachelor of Secondary Education (BSEd) major in English", d));
                    programs.add(new Program("Bachelor of Secondary Education (BSEd) major in Mathematics", d));
                    programs.add(new Program("Bachelor of Secondary Education (BSEd) major in Science", d));
                    programs.add(new Program("Bachelor of Secondary Education (BSEd) major in Filipino", d));
                    programs.add(new Program("Bachelor of Secondary Education (BSEd) major in Social Studies", d));
                    programs.add(new Program("Bachelor of Elementary Education (BEEd)", d));
                    programs.add(new Program("Bachelor of Early Childhood Education (BECEd)", d));
                    programs.add(new Program("Master of Arts in Education Management (MAEM)", d));
                });

                deptRepo.findByName("College of Engineering (CE)").ifPresent(d -> {
                    programs.add(new Program("Bachelor of Science in Civil Engineering (BSCE)", d));
                    programs.add(new Program("Bachelor of Science in Computer Engineering (BSCpE)", d));
                    programs.add(new Program("Bachelor of Science in Electrical Engineering (BSEE)", d));
                    programs.add(new Program("Bachelor of Science in Electronics Engineering (BSECE)", d));
                    programs.add(new Program("Bachelor of Science in Industrial Engineering (BSIE)", d));
                    programs.add(new Program("Bachelor of Science in Mechanical Engineering (BSME)", d));
                    programs.add(new Program("Bachelor of Science in Railway Engineering (BSRE)", d));
                });

                deptRepo.findByName("College of Human Kinetics (CHK)").ifPresent(d -> {
                    programs.add(new Program("Bachelor of Physical Education (BPE)", d));
                    programs.add(new Program("Bachelor of Science in Exercises and Sports (BSESS)", d));
                });

                deptRepo.findByName("College of Law (CL)").ifPresent(d -> {
                    programs.add(new Program("Juris Doctor (JD)", d));
                });

                deptRepo.findByName("College of Political Science and Public Administration (CPSPA)").ifPresent(d -> {
                    programs.add(new Program("Bachelor of Public Administration (BPA)", d));
                    programs.add(new Program("Bachelor of Arts in International Studies (BAIS)", d));
                    programs.add(new Program("Bachelor of Arts in Political Economy (BAPE)", d));
                    programs.add(new Program("Bachelor of Arts in Political Science (BAPS)", d));
                });

                deptRepo.findByName("College of Social Sciences and Development (CSSD)").ifPresent(d -> {
                    programs.add(new Program("Bachelor of Arts in History (BAH)", d));
                    programs.add(new Program("Bachelor of Arts in Sociology (BAS)", d));
                    programs.add(new Program("Bachelor of Science in Cooperatives (BSC)", d));
                    programs.add(new Program("Bachelor of Science in Economics (BSE)", d));
                    programs.add(new Program("Bachelor of Science in Psychology (BSPSY)", d));
                });

                deptRepo.findByName("College of Science (CS)").ifPresent(d -> {
                    programs.add(new Program("Bachelor of Science Food Technology (BSFT)", d));
                    programs.add(new Program("Bachelor of Science in Applied Mathematics (BSAPMATH)", d));
                    programs.add(new Program("Bachelor of Science in Biology (BSBIO)", d));
                    programs.add(new Program("Bachelor of Science in Chemistry (BSCHEM)", d));
                    programs.add(new Program("Bachelor of Science in Mathematics (BSMATH)", d));
                    programs.add(new Program("Bachelor of Science in Nutrition and Dietetics (BSND)", d));
                    programs.add(new Program("Bachelor of Science in Physics (BSPHY)", d));
                    programs.add(new Program("Bachelor of Science in Statistics (BSSTAT)", d));
                });

                deptRepo.findByName("College of Tourism, Hospitality and Transportation Management (CTHTM)").ifPresent(d -> {
                    programs.add(new Program("Bachelor of Science in Hospitality Management (BSHM)", d));
                    programs.add(new Program("Bachelor of Science in Tourism Management (BSTM)", d));
                    programs.add(new Program("Bachelor of Science in Transportation Management (BSTRM)", d));
                });

                deptRepo.findByName("Institute of Technology").ifPresent(d -> {
                    programs.add(new Program("Diploma in Civil Engineering Technology (DCvET)", d));
                    programs.add(new Program("Diploma in Computer Engineering Technology (DCET)", d));
                    programs.add(new Program("Diploma in Electrical Engineering Technology (DEET)", d));
                    programs.add(new Program("Diploma in Electronics Engineering Technology (DECET)", d));
                    programs.add(new Program("Diploma in Information Communication Technology (DICT)", d));
                    programs.add(new Program("Diploma in Mechanical Engineering Technology (DMET)", d));
                    programs.add(new Program("Diploma in Office Management Technology (DOMT)", d));
                    programs.add(new Program("Diploma in Railway Engineering Technology (DRET)", d));
                });

                programRepo.saveAll(programs);
                System.out.println("ProgramSeeder: Seeded " + programs.size() + " programs.");
            } else {
                System.out.println("ProgramSeeder: Programs already exist. Skipping seed.");
            }
        };
    }
}