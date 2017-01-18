package sec.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.MonkepoHunter;

public interface MonkepoHunterRepository extends JpaRepository<MonkepoHunter, Long> {

    MonkepoHunter findByUsername(String username);

    MonkepoHunter findByIdCode(String idCode);
}
