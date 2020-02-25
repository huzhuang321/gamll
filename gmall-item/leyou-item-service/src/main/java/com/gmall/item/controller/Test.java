package com.gmall.item.controller;

import com.gmall.item.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 拼接sql测试类
 */
@RestController
public class Test {
    @Autowired
    private TestService testService;

    @RequestMapping("t")
    public Object test() {
        Object catalog1 = testService.getCatalog1();
        return catalog1;
    }

    public static void main(String[] args) {
        List<String> orgs = Arrays.asList("SELECT", "medical_org_name", "from"
                , "t_dataset_level_hos_table_num", "GROUP", "BY", "medical_org_name", "LIMIT",
                "0", ",", "10", ";");

        StringBuilder stringBuilder = new StringBuilder();
        String sql = null;

        stringBuilder.append("SELECT\n" +
                "b.dataset_table_num as total,\n" +
                "a.dataset_struc_id \n");
        for (String org : orgs) {
            sql = String.format("SUM(CASE `medical_org_name` WHEN '罗源县医院' THEN dataset_hos_table_num ELSE 0 END) AS '罗源县医院', \n",
            org);
            stringBuilder.append(sql);

        }
        stringBuilder.append("a.dataset_struc_desc \n" + "FROM\n" +
                " t_dataset_level_hos_table_num  a left join t_dataset_level_table_num b on a.dataset_struc_id =b.dataset_struct_id\n" +
                "\n" +
                "GROUP BY a.dataset_struc_id;\n");
        System.out.println(stringBuilder.toString());
    }
}

