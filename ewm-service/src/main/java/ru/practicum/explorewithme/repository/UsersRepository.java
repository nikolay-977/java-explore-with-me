package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.model.User;

public interface UsersRepository extends JpaRepository<User, Long> {

}
