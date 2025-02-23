package com.func.spring;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.FactoryBean;

import java.text.SimpleDateFormat;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DateFactory implements FactoryBean<Date> {
    private String strDate;

    @Override
    public Date getObject() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date birth = sdf.parse(this.strDate);
        return birth;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
