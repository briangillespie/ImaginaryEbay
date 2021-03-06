package com.imaginaryebay.Controller;

import com.imaginaryebay.Models.Item;
import com.imaginaryebay.Models.Userr;
import com.imaginaryebay.Repository.UserrRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Ben_Big on 6/28/16.
 */


@ComponentScan("com.imaginaryebay.Repository")

public class UserrControllerImpl implements UserrController {

    @Autowired
    private UserrRepository userrRepository;

    @Override
    public void createNewUserr(Userr userr){
        //ToDo: should have a mechanism here to check if the user exists or not
        userrRepository.createNewUserr(userr);
    }

    @Override
    public Userr getUserrByID(Long id){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        boolean isAdmin=auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        Userr userr=userrRepository.getUserrByID(id);
        if(userr==null){
            return null;
        }
        else if( userr.getEmail().equals(email) | isAdmin){
            return userr;
        }
        else{
            //ToDo: 401 should be returned here
            return null;
        }
    }



    @Override
    public Userr getUserrByEmail(String email){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String logInEmail=auth.getName();
        boolean isAdmin=auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        if (email.equals(logInEmail) | isAdmin) {
            Userr userr = userrRepository.getUserrByEmail(email);
            return userr;
        }
        else{
            //ToDo: 401 should be returned here
            return null;
        }

    }

    @Override
    public List<Userr> getAllUserrs (){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin=auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        if (isAdmin) {
            return userrRepository.getAllUserrs();
        }
        else{
            //ToDo: 401 should be returned here
            return null;
        }
    }

    @Override
    public List<Userr> getUserrByName(String name){

        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        boolean isAdmin=auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        List<Userr> userrList=userrRepository.getUserrByName(name);
        if (isAdmin){
            return userrList;
        }
        Userr userrConcerned=null;
        Iterator<Userr> itr=userrList.iterator();
        while(itr.hasNext()){
            Userr temp=itr.next();
            if(temp.getEmail().equals(email)){
                userrConcerned=temp;
                break;
            }
        }
        List<Userr> result =new ArrayList<Userr>();
        result.add(userrConcerned);
        return result;
    }

    @Override
    public Userr updateUserrByID(Long id, Userr u){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String email=auth.getName();
        boolean isAdmin=auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
        Userr temp=userrRepository.getUserrByID(id);
        if(temp==null){
            return null;
        }
        else if (isAdmin) {
            return userrRepository.updateUserrByID(id, u);
        }
        else if (temp.getEmail().equals(email)){
            return userrRepository.updateUserrByID(id, u);
        }
        else{
            //ToDo: should return 401
                return null;
        }
    }

    @Override
    public List<Item> getItemsSoldByThisUser(Long id){
        return  userrRepository.getItemsSoldByThisUser(id);
    }

}
