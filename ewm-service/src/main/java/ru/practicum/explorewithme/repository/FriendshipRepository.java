package ru.practicum.explorewithme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.model.Friendship;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Optional<Friendship> findFriendshipByUserIdAndAndFriendId(Long userId, Long friendId);

    Optional<List<Friendship>> findFriendshipByUserId(Long userId);
}
