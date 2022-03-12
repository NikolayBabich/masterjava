package ru.javaops.masterjava.persist.service;

import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.UserDao;
import ru.javaops.masterjava.persist.model.User;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserService {

    private static final UserDao dao = DBIProvider.getDao(UserDao.class);

    public List<User> insertBatch(List<User> users, int chunkSize) {
        int[] insertedIds = dao.insertBatch(users, chunkSize);
        Set<String> insertedEmails = dao.getByIds(insertedIds).stream()
                .map(User::getEmail)
                .collect(Collectors.toSet());

        List<String> duplicateEmails = users.stream()
                .map(User::getEmail)
                .filter(email -> !insertedEmails.contains(email))
                .collect(Collectors.toList());

        return dao.getByEmails(duplicateEmails);
    }

    public List<User> getWithLimit(int limit) {
        return dao.getWithLimit(limit);
    }
}
