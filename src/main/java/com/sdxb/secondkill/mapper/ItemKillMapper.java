package com.sdxb.secondkill.mapper;

import com.sdxb.secondkill.entity.ItemKill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @ClassName: ItemKillMapper
 * @Description: TODO
 * @Create by: Liyu
 * @Date: 2020/7/22 17:35
 */
@Mapper
public interface ItemKillMapper {

    @Select("select \n" +
            "a.*,\n" +
            "b.name as itemName,\n" +
            "(\n" +
            "\tcase when(now() BETWEEN a.start_time and a.end_time and a.total>0)\n" +
            "\t\tthen 1\n" +
            "\telse 0\n" +
            "\tend\n" +
            ")as cankill\n" +
            "from item_kill as a left join item as b\n" +
            "on a.item_id = b.id\n" +
            "where a.is_active=1;")
    List<ItemKill> selectAll();

    @Select("select \n" +
            "a.*,\n" +
            "b.name as itemName,\n" +
            "(\n" +
            "\tcase when(now() BETWEEN a.start_time and a.end_time and a.total>0)\n" +
            "\t\tthen 1\n" +
            "\telse 0\n" +
            "\tend\n" +
            ")as cankill\n" +
            "from item_kill as a left join item as b\n" +
            "on a.item_id = b.id\n" +
            "where a.is_active=1 and a.id=#{id};")
    ItemKill selectByid(Integer id);

    @Update("update item_kill set total=total-1 where id=#{killId}")
    int updateKillItem(Integer killId);

//    @Select("select \n" +
//            "a.*,\n" +
//            "b.name as itemName,\n" +
//            "(\n" +
//            "\tcase when(now() BETWEEN a.start_time and a.end_time and a.total>0)\n" +
//            "\t\tthen 1\n" +
//            "\telse 0\n" +
//            "\tend\n" +
//            ")as cankill\n" +
//            "from item_kill as a left join item as b\n" +
//            "on a.item_id = b.id\n" +
//            "where a.is_active=1 and a.id=#{id} and a.total>0;")
//    ItemKill selectByidV2(Integer killId);
//
//    @Update("update item_kill set total=total-1 where id=#{killId} and total>0")
//    int updateKillItemV2(Integer killId);
}
