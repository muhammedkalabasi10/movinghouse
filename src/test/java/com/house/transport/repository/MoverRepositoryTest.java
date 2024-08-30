package com.house.transport.repository;

import com.house.transport.config.TestApplication;
import com.house.transport.model.Mover;
import com.house.transport.repository.MoverRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.validation.ConstraintViolationException;

import java.util.List;

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
                0,
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

}
