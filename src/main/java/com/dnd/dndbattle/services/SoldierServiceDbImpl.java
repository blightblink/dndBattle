package com.dnd.dndbattle.services;

import com.dnd.dndbattle.domain.Soldier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

@Service
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class SoldierServiceDbImpl implements SoldierService{

    private JdbcTemplate atsJdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(SoldierServiceDbImpl.class);
    private Map<Long, Soldier> soldiers = new HashMap<>(); //internal cache

    private static String DELETE_QUERY = "delete from dnd_soldiers where id = ?";
    private static String INSERT_QUERY = "insert into dnd_soldiers(id,name,ac,bab,damageroll,str,dex,con,hprolldie,lvl) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static String UPDATE = "update dnd_soldiers where id = ?";

    @Autowired
    @Qualifier("dnddb")
    public void setAtsJdbcTemplate(JdbcTemplate atsJdbcTemplate) {
        this.atsJdbcTemplate = atsJdbcTemplate;
    }

    @PostConstruct
    void init(){
        initCache();
        logger.info("SoldierServiceDbImpl bean instatiated");
    }

    @Override
    public Soldier getByName(String name) {
        return null;
    }

    @Override
    public List<Soldier> listAll() {
        return new ArrayList<>(soldiers.values());
    }

    @Override
    public Soldier getById(Long id) {
        return soldiers.get(id);
    }

    @Override
    public Soldier saveOrUpdate(Soldier soldier) {
        this.deleteById(soldier.getId());//TODO nasty hack untill hibernate ;)
        if(soldier.getId() == null) {
            soldier.setId(soldiers.keySet().stream().max(Long::compare).get() + 1 );
        }
        soldiers.put(soldier.getId(),soldier);

        int status = atsJdbcTemplate.update(INSERT_QUERY,soldier.getId(),soldier.getName(),soldier.getAc(),soldier.getBab(),
                soldier.getDamage(),soldier.getStr(),soldier.getDex(),soldier.getCon(),soldier.getHd(),soldier.getLevel());

        return status !=0 ? soldier : null;
    }

    @Override
    public void deleteById(Long id) {
        soldiers.remove(id);
        int status = atsJdbcTemplate.update(DELETE_QUERY,id);


    }

    private void initCache(){
        List<Soldier> temp = new ArrayList<>();

        final String sql = "SELECT id,name,ac,bab,damageroll,str,dex,con,hprolldie,lvl FROM dnd_soldiers " ;

        try {
            /*temp = atsJdbcTemplate.query(sql,
                    (rs, rowNum) -> toSoldier(rs))
                    .stream()
                    .collect(Collectors.toList());*/
            soldiers = atsJdbcTemplate.query(sql,
                    (rs, rowNum) -> toSoldier(rs))
                    .stream()
                    .collect(Collectors.toMap(Soldier::getId, x -> x));
        } catch (final Exception e) {
            logger.error("JDBCTemplate Error", e);
        }

        //soldiers = temp.stream().collect(Collectors.toMap(Soldier::getId,x -> x));

        //return map;
    }

    private Soldier toSoldier(ResultSet rs) throws SQLException {
        Soldier soldier = new Soldier();
        soldier.setId(rs.getLong(1));
        soldier.setName(rs.getString(2));
        soldier.setAc(rs.getInt(3));
        soldier.setBab(rs.getInt(4));
        soldier.setDamage(rs.getInt(5));
        soldier.setStr(rs.getInt(6));
        soldier.setDex(rs.getInt(7));
        soldier.setCon(rs.getInt(8));
        soldier.setHd(rs.getInt(9));
        soldier.setLevel(rs.getInt(10));
        return soldier;
    }
}
