package com.chero.bserver.sso.model.pojo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import java.util.*;

/**
 * 根基客户端编号（client_id)查询出客户的信息以及对应的授权内容
 * key = client、value = Client的对象，如果不存在则用null;
 * key = allAuthorities、value = 当Client信息存在时获取授权内容;
 * Created by hxh on 2018/5/11.
 */
@Data
@NoArgsConstructor
public class ClientDTO extends BaseClientDetails{

    private Integer locked;

}
