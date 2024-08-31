package com.house.transport.repository;

import com.house.transport.config.TestApplication;
import com.house.transport.model.Mover;
import com.house.transport.repository.MoverRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.validation.ConstraintViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = TestApplication.class)
public class MoverRepositoryTest {

    @Autowired
    private MoverRepository moverRepository;

    @Test
    public void createMoverSuccess() {
        Mover mover = new Mover(
                null,
                "mover@example.com",
                "123 (456) 789-0123",
                "123.Dg_d",
                "Mover Co.",
                "logo.png",
                "We are a reliable moving company.",
                "Licenses Info",
                new byte[0],
                new byte[0],
                "1234567890",
                5,
                List.of()
        );

        Mover createdMover = moverRepository.save(mover);

        assertThat(createdMover)
                .extracting(
                        Mover::getEmail,
                        Mover::getPhone,
                        Mover::getPassword,
                        Mover::getCompany_name,
                        Mover::getLogo,
                        Mover::getAbout,
                        Mover::getLicences_information,
                        Mover::getVkn,
                        Mover::getMax_floor
                )
                .containsExactly(
                        mover.getEmail(),
                        mover.getPhone(),
                        mover.getPassword(),
                        mover.getCompany_name(),
                        mover.getLogo(),
                        mover.getAbout(),
                        mover.getLicences_information(),
                        mover.getVkn(),
                        mover.getMax_floor()
                );
    }

    @Test
    public void fetchMoversSucces(){
        List<Mover> moverList = new ArrayList<>();
        int listSize = 3;
        for(int i=0;i<listSize;i++){
            moverList.add(new Mover(
                    null,
                    "testmail"+i+"@gmail.com",
                    String.valueOf(i).repeat(10),
                    "Password*"+i,
                    "testcompany"+i,
                    "testlogo"+i,
                    "test about text "+i,
                    "test licences information "+i,
                    new byte[]{0x01, 0x02, 0x03, 0x04},
                    new byte[]{0x02, 0x03, 0x04, 0x05},
                    String.valueOf(i).repeat(10),
                    i,
                    null
            ));
        }
        moverRepository.saveAllAndFlush(moverList);
        List<Mover> fetchedMover = moverRepository.findAll();
        int testMoverIndex = 0;
        assertThat(fetchedMover)
                .hasSize(listSize)
                .extracting(
                        Mover::getEmail,
                        Mover::getPhone,
                        Mover::getVkn
                ).contains(new Tuple(
                        fetchedMover.get(testMoverIndex).getEmail(),
                        fetchedMover.get(testMoverIndex).getPhone(),
                        fetchedMover.get(testMoverIndex).getVkn()
                ));
    }

    @Test
    public void getMoverById(){
        Mover mover = new Mover(
                null,
                "testmover@example.com",
                "05343343434",
                "TestParola_1234",
                "Kalabasi Company",
                "examplelogo.png",
                "sample about text",
                "Sample information",
                new byte[0],
                new byte[0],
                "1234567890",
                2,
                null
        );
        Mover createdMover = moverRepository.saveAndFlush(mover);
        Optional<Mover> foundMover = moverRepository.findById(createdMover.getId());
        assertThat(foundMover)
                .get()
                .usingRecursiveComparison().isEqualTo(createdMover);
    }

    @Test
    public void updateMoverSuccess(){
        Mover mover = new Mover(
                null,
                "testmover@example.com",
                "05343343434",
                "TestParola_1234",
                "Kalabasi Company",
                "examplelogo.png",
                "sample about text",
                "Sample information",
                new byte[0],
                new byte[0],
                "1234567890",
                2,
                null
        );
        Mover createdMover = moverRepository.saveAndFlush(mover);
        createdMover.setEmail("updateemail@example.com");
        createdMover.setPhone("02164220606");
        createdMover.setCompany_name("updated company name");
        Mover updatedMover = moverRepository.saveAndFlush(mover);
        assertThat(updatedMover)
                .extracting(Mover::getEmail,Mover::getPhone,Mover::getCompany_name)
                .containsExactly(createdMover.getEmail(),createdMover.getPhone(),createdMover.getCompany_name());
    }

    @Test
    public void deleteMoverSuccess(){
        Mover mover = new Mover(
                null,
                "testmover@example.com",
                "05343343434",
                "TestParola_1234",
                "Kalabasi Company",
                "examplelogo.png",
                "sample about text",
                "Sample information",
                new byte[0],
                new byte[0],
                "1234567890",
                2,
                null
        );
        Mover createdMover = moverRepository.saveAndFlush(mover);
        moverRepository.delete(createdMover);
        Optional<Mover> deletedMover = moverRepository.findById(createdMover.getId());
        assertThat(deletedMover).isNotPresent();
    }

}
