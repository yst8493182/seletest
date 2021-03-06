/*
This file is part of the Seletest by Papadakis Giannis <gpapadakis84@gmail.com>.

Copyright (c) 2014, Papadakis Giannis <gpapadakis84@gmail.com>
All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.automation.seletest.core.testNG;

import java.lang.reflect.Method;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import com.automation.seletest.core.services.utilities.FilesUtils;
import com.automation.seletest.core.spring.ApplicationContextProvider;


/**
 * DataSources class
 * @author Giannis Papadakis (mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
public class DataSources {

    /**Constant for excel file*/
    private final static String EXCEL="xls";

    /**Constant for excel sheet*/
    private final static String EXCELSHEET="xlsSheet";

    /**Constant for excel table*/
    private final static String EXCELTABLE="xlsTable";

    private static FilesUtils file;

    @Autowired
    private FilesUtils wiredfile;

    @PostConstruct
    public void init() {
        DataSources.file = wiredfile;
    }

    /**
     * Generic DataProvider that returns data from a Map
     * @param method
     * @return Object[][] with the Map that contains properties
     * @throws Exception
     */
    @DataProvider(name = "GenericDataProvider")
    public static Object[][] getDataProvider(final Method method) throws Exception {
        Map<String, String> map = ApplicationContextProvider.getApplicationContext().getBean(FilesUtils.class).readData(method);
        return new Object[][] { { map } };
    }

    @DataProvider(name = "ExcelDataProvider",parallel=true)
    public static Object[][] createData(ITestContext context) throws Exception{
        String testParam = context.getCurrentXmlTest().getParameter(EXCEL);
        String testParamSheet = context.getCurrentXmlTest().getParameter(EXCELSHEET);
        String testParamTable = context.getCurrentXmlTest().getParameter(EXCELTABLE);
        Object[][] retObjArr=file.getTableArray(testParam,testParamSheet,testParamTable);
        return(retObjArr);
    }






}
