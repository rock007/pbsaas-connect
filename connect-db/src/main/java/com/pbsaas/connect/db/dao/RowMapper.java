package com.pbsaas.connect.db.dao;


public interface RowMapper<T> {

    T mapRow(Object[] objArray);

}
