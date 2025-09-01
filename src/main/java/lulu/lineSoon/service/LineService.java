package lulu.lineSoon.service;

import lulu.lineSoon.model.User;
import lulu.lineSoon.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LineService {
    private final LineRepository repo;
    @Autowired
    public LineService(LineRepository repo) { this.repo = repo; }

    @Transactional
    public User enqueue(String nickname) {
        User u = new User();
        u.setNickname(nickname);
        return repo.save(u);
    }

    @Transactional
    public void cancel(String nickname) {
        var u = repo.findTopByNicknameAndStatusOrderByCreatedAtDesc(nickname, "WAITING")
                .orElseThrow(() -> new IllegalArgumentException("대기 중 아님"));
        u.setStatus("CANCELED");
    }

    @Transactional
    public User dequeue() {
        var list = repo.findAllByStatusOrderByCreatedAtAsc("WAITING");
        if (list.isEmpty()) throw new IllegalStateException("대기열이 비어있음");
        var first = list.get(0);
        first.setStatus("SERVED");
        return first;
    }

    @Transactional
    public void removeByOrder(int order) {
        var list = repo.findAllByStatusOrderByCreatedAtAsc("WAITING");
        if (order < 1 || order > list.size()) throw new IllegalArgumentException("순서 범위 밖");
        var target = list.get(order - 1);
        target.setStatus("CANCELED");
    }

    @Transactional(readOnly = true)
    public int getPosition(String nickname) {
        var me = repo.findTopByNicknameAndStatusOrderByCreatedAtDesc(nickname, "WAITING")
                .orElseThrow(() -> new IllegalArgumentException("대기 중 아님"));
        var list = repo.findAllByStatusOrderByCreatedAtAsc("WAITING"); // createdAt ASC
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(me.getId())) return i + 1;
        }
        return -1;
    }

    @Transactional(readOnly = true)
    public List<User> waitingList() {
        return repo.findAllByStatusOrderByCreatedAtAsc("WAITING");
    }
}
