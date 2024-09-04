package com.house.transport.security.detailsservice;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
public class ComposedDetailsService implements UserDetailsService {

    @Autowired
    private final CustomerDetailsService customerDetailsService;

    @Autowired
    private final MoverDetailsService moverDetailsService;

    private List<UserDetailsService> serviceList;

    @PostConstruct
    public void setServices(){
        List<UserDetailsService> newServices = new ArrayList<>();
        newServices.add(customerDetailsService);
        newServices.add(moverDetailsService);
        this.serviceList = newServices;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        for (UserDetailsService service : serviceList){
            try {
                UserDetails userDetails = service.loadUserByUsername(username);
                return userDetails;
            }catch (UsernameNotFoundException ex){
                continue;
            }
        }
        throw new UsernameNotFoundException("User Not Found");
    }
}
