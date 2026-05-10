package com.example.system.mapper;

import com.example.common.annotation.TenantIgnore;
import com.example.system.domain.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@TenantIgnore
@Mapper
public interface AdminMessageMapper {

    List<Message> queryMessageByReceiverId(@Param("receiverId") Long receiverId);

    Long countUnread(@Param("receiverId") Long receiverId);

    void markAsRead(@Param("id") Long id);

    void markAllAsRead(@Param("receiverId") Long receiverId);

    void addMessage(Message message);

    void batchDeleteMessage(@Param("list") List<Long> ids);
}
