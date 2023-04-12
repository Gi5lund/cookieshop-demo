package com.example.cookieshop.controllers;

import com.example.cookieshop.models.Basket;
import com.example.cookieshop.models.Cookie;
import com.example.cookieshop.repositories.CookieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CookieController {
    CookieRepository repo=new CookieRepository();

    @GetMapping("/")
    public String index(HttpSession session,Model basketModel){
        Basket basket= (Basket) session.getAttribute("basket");
        if (basket==null){
            basket=new Basket(new ArrayList<>());
        }
        session.setAttribute("basket",basket);
        return "index";
    }

    @GetMapping("/basket")
    public String basket(HttpSession session, Model basketModel){
        Basket basket= (Basket) session.getAttribute("basket");
        if (basket==null){
            basket=new Basket(new ArrayList<>());
        }
        session.setAttribute("basket",basket);

        int total=0;
        for(Cookie cookie:basket.getCookieList()){
            total +=cookie.getPrice();
        }
        basketModel.addAttribute("total",total);
        return "basket";
    }

    @GetMapping("/shop")
    public String shop(HttpSession session, Model cookieModel){
        cookieModel.addAttribute("cookies",repo.getAllCookies());
        return "shop";
    }
    @GetMapping("/addToBasket")
    public String add( HttpSession session, @RequestParam int id){
        Cookie cookie=repo.getCookieById(id);
        //cookie.setId(id);
        Basket basket=(Basket) session.getAttribute("basket");
        if(basket==null){
            basket=new Basket(new ArrayList<>());}
            List<Cookie> cookies =basket.getCookieList();
        cookies.add(cookie);
        basket.setCookieList(cookies);
        session.setAttribute("basket",basket);

        return "redirect:/basket";
    }
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,@RequestParam("pwd") String password){
        if (username.equals("Morten") && password.equals("123")){
            return "testretur";

        }
        return "login";
    }
    @GetMapping("/login")
    public String showLogin(){
        return "/login";
    }
//    @GetMapping("/addToBasket")
//    public String add(@RequestParam String id){
//        return "";
//    }
}
