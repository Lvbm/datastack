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

package org.apache.datastack.console.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.datastack.console.base.domain.RestRequest;
import org.apache.datastack.console.system.entity.Team;
import org.apache.datastack.console.system.mapper.TeamMapper;
import org.apache.datastack.console.system.service.TeamService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

    @Override
    public IPage<Team> findTeams(Team team, RestRequest request) {
        Page<Team> page = new Page<>();
        page.setCurrent(request.getPageNum());
        page.setSize(request.getPageSize());
        return this.baseMapper.findTeam(page, team);
    }

    @Override
    public Team findByName(String teamName) {
        return baseMapper.selectOne(new LambdaQueryWrapper<Team>().eq(Team::getTeamName, teamName));
    }

    @Override
    public void createTeam(Team team) {
        Team existedTeam = findByName(team.getTeamName());
        if (existedTeam != null) {
            throw new IllegalArgumentException(String.format("Team name [%s] was found, please rename and try again.", team.getTeamName()));
        }
        team.setId(null);
        team.setCreateTime(new Date());
        team.setModifyTime(team.getCreateTime());
        this.save(team);
    }

    @Override
    public void deleteTeam(Team team) {
        this.removeById(team);
    }

    @Override
    public void updateTeam(Team team) {
        Team oldTeam = Optional.ofNullable(this.getById(team))
            .orElseThrow(() -> new IllegalArgumentException(String.format("Team id [id=%s] not found", team.getId())));
        oldTeam.setDescription(team.getDescription());
        oldTeam.setModifyTime(new Date());
        updateById(oldTeam);
    }

    @Override
    public List<Team> findUserTeams(Long userId) {
        return baseMapper.findUserTeams(userId);
    }

}
