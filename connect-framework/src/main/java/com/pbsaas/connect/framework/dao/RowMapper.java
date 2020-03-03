package com.pbsaas.connect.framework.dao;


public interface RowMapper<T> {

    T mapRow(Object[] objArray);

}
