/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.he3cloud.datastack.service.server.system.controller;

import com.he3cloud.datastack.service.server.base.domain.RestRequest;
import com.he3cloud.datastack.service.server.base.domain.RestResponse;
import com.he3cloud.datastack.service.server.system.entity.Team;
import com.he3cloud.datastack.service.server.system.service.TeamService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Slf4j
@Validated
@RestController
@RequestMapping("team")
public class TeamController {

    @Resource
    private TeamService teamService;

    @PostMapping("list")
    public RestResponse teamList(RestRequest restRequest, Team team) {
        IPage<Team> teamList = teamService.findTeams(team, restRequest);
        return RestResponse.success(teamList);
    }

    @PostMapping("check/name")
    public RestResponse checkTeamName(@NotBlank(message = "{required}") String teamName) {
        Team result = this.teamService.findByName(teamName);
        return RestResponse.success(result == null);
    }

    @PostMapping("post")
    public RestResponse addTeam(@Valid Team team) {
        this.teamService.createTeam(team);
        return RestResponse.success();
    }

    @DeleteMapping("delete")
    public RestResponse deleteTeam(Team team) {
        this.teamService.deleteTeam(team);
        return RestResponse.success();
    }

    @PutMapping("update")
    public RestResponse updateTeam(Team team) {
        this.teamService.updateTeam(team);
        return RestResponse.success();
    }

}
