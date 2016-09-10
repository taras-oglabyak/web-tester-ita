package com.softserve.webtester.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.softserve.webtester.exception.ResourceNotFoundException;
import com.softserve.webtester.mapper.EnvironmentMapper;
import com.softserve.webtester.model.Environment;

public class EnvironmentServiceTest {

    private Environment environment;

    @Mock
    private EnvironmentMapper environmentMapper;

    @InjectMocks
    private EnvironmentService environmentService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidLoad() {
        int validEnvironmentId = 10;
        when(environmentMapper.load(validEnvironmentId)).thenReturn(EnvironmentInstanceTestProvider.getEnvironment("valid Name"));
        assertEquals("valid Name", environmentService.load(validEnvironmentId).getName());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testInValidLoad() {
        int inValidEnvironmentId = 1000;
        when(environmentMapper.load(inValidEnvironmentId)).thenReturn(environment);
        environmentService.load(inValidEnvironmentId);
    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerExceptionLoad() {
        Integer inValidEnvironmentId = null;
        environmentService.load(inValidEnvironmentId);
    }

    @Test
    public void testLoadAll() {
        List<Environment> listEnvironmetns = new ArrayList<Environment>();
        listEnvironmetns.add(EnvironmentInstanceTestProvider.getEnvironment("First"));
        listEnvironmetns.add(EnvironmentInstanceTestProvider.getEnvironment("Second"));

        when(environmentMapper.loadAll()).thenReturn(listEnvironmetns);
        assertTrue(environmentService.loadAll().size() == 2);

    }

    @Test
    public void testValidSave() {
        assertEquals(20, environmentService.save(EnvironmentInstanceTestProvider.getEnvironment(20)));
    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerExceptionSave() {
        environmentService.save(environment);
    }

    @Test
    public void testValidUpdate() {
        assertEquals(20, environmentService.update(EnvironmentInstanceTestProvider.getEnvironment(20)));
    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerExceptionUpdate() {
        environmentService.update(environment);
    }

    @Test
    public void testValidDelete() {
        assertEquals(20, environmentService.delete(EnvironmentInstanceTestProvider.getEnvironment(20)));
    }

    @Test(expected = NullPointerException.class)
    public void testNullPointerExceptionDelete() {
        environmentService.delete(environment);
    }

    @Test
    public void testValidIsNameFree() {
        environment = EnvironmentInstanceTestProvider.getEnvironment("free Name");
        when(environmentMapper.isNameFree(environment)).thenReturn(2);
        assertEquals(2, environmentService.isNameFree(environment));
    }

    @Test
    public void testInitConnectionPools() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testCheckConnection() {
        fail("Not yet implemented"); // TODO
    }

}
