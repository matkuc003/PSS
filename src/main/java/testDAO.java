import models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import repositories.RoleRepo;

import javax.annotation.PostConstruct;

@Service
public class testDAO {
    @Autowired
    RoleRepo roleRepo;
    @PostConstruct
    public void init() {
        save(new Role(0l,"ROLE_USER"));
    }
public boolean save (Role role){
    roleRepo.save(role);
    return false;
}
}
