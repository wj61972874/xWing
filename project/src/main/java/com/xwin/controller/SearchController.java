package com.xwin.controller;

import com.xwin.pojo.HotSearch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/search")
public class SearchController {



    public List<HotSearch> hotSearch(){

        return null;
    }
}
