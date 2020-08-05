package com.sdxb.secondkill.mapper;

import com.sdxb.secondkill.dto.KillSuccessUserDto;
import com.sdxb.secondkill.entity.ItemKillSuccess;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @ClassName: ItemKillSuccessMapper
 * @Description: TODO 订单抢购相关数据库操作
 * @Create by: Liyu
 * @Date: 2020/7/26 10:30
 */
@Mapper
public interface ItemKillSuccessMapper {

    @Select("select count(1) from item_kill_success where user_id=#{userId} and kill_id=#{killId} and status in (0)")
    int countByKillUserId(@Param("killId") Integer killId, @Param("userId") Integer userId);

    @Insert("insert into item_kill_success(code,item_id,kill_id,user_id,status,create_time) values(#{code},#{itemId},#{killId},#{userId},#{status},#{createTime})")
    int insertSelective(ItemKillSuccess entity);

    @Select("select a.*,b.user_name,b.phone,b.email,c.name as itemName\n" +
            "from item_kill_success as a\n" +
            "left join user b on b.id=a.user_id\n" +
            "left join item c on c.id=a.item_id\n" +
            "where a.code=#{orderNo} and b.is_active=1")
    KillSuccessUserDto selectByCode(String orderNo);


    @Select("select * from item_kill_success where code=#{code}")
    ItemKillSuccess selectByPrimaryKey(String code);

    @Update("update item_kill_success set status=-1 where code=#{code} and status=0")
    void expireOrder(String code);

    @Select("select a.*,timestampdiff(minute,a.create_time,now()) as diffTime from item_kill_success as a where status='0'")
    List<ItemKillSuccess> selectExpireOrders();

}
