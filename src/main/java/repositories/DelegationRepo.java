package repositories;

import models.Delegation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface DelegationRepo extends JpaRepository<Delegation,Long> {

}
