package com.sensor.service.controller;

import com.sensor.service.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by sindhya on 11/12/16.
 */

@CrossOrigin
@RestController
public class SensorServiceController {


    @RequestMapping(value="/vendors",method= RequestMethod.GET)
    public List vendor(@RequestParam(value = "vendor_email",required = false,defaultValue = "0")String vendor_email){

        VendorDAO vendorDAO = new VendorDAO();
        List<Vendor> vendorList=vendorDAO.vendorList(vendor_email);
        return vendorList;

    }

    @RequestMapping(value="/users",method=RequestMethod.GET)
    public List users(@RequestParam(value = "user_email",required = false,defaultValue = "0") String user_email){

        UserDAO userDAO=new UserDAO();
        List<Users> usersList=userDAO.userList(user_email);
        return usersList;

    }

    @RequestMapping(value="/sensors",method=RequestMethod.GET)
    public List sensors(@RequestParam(value = "vendor_email", required = false,defaultValue = "0") String vendor_email){
        SensorDAO sensorDAO=new SensorDAO();
        List<Sensor> sensorList=sensorDAO.sensorList(vendor_email);
        return sensorList;
    }


    @RequestMapping(value="/plans",method=RequestMethod.GET)
    public List user_plans(@RequestParam(value = "plan_id", required = false,defaultValue = "0") Integer plan_id){

        PlanDAO planDAO=new PlanDAO();
        List<Plan> planList=planDAO.planList(plan_id);
        return planList;

    }

    @RequestMapping(value = "/vendors",method=RequestMethod.POST)
    public ResponseEntity<Vendor> vendor_save(@RequestBody Vendor vendor){

        VendorDAO vendorDAO=new VendorDAO();
        vendorDAO.createVendor(vendor);

        return new ResponseEntity<Vendor>(vendor,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/users",method=RequestMethod.POST)
    public ResponseEntity<Users> user_save(@RequestBody Users user){

        UserDAO userDAO=new UserDAO();
        userDAO.createUser(user);

        return new ResponseEntity<Users>(user,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/sensors",method=RequestMethod.POST)
    public ResponseEntity<Sensor> sensor_save(@RequestBody Sensor sensor){

        SensorDAO sensorDAO=new SensorDAO();
        sensorDAO.createSensor(sensor);

        return new ResponseEntity<Sensor>(sensor,HttpStatus.CREATED);
    }

    @RequestMapping(value="/sensors/{id}",method=RequestMethod.PUT)
    public ResponseEntity<Sensor> updateSensor(@PathVariable("id") int id,@RequestBody Sensor sensor){

        SensorDAO sensorDAO=new SensorDAO();
        Sensor newSensor=sensorDAO.findBySensorId(id);
        if(newSensor==null){
            return new ResponseEntity<Sensor>(HttpStatus.NOT_FOUND);
        }

        newSensor.setSensorId(id);
        newSensor.setSensorName(sensor.getSensorName());
        newSensor.setSensorType(sensor.getSensorType());
        newSensor.setSensorLocation(sensor.getSensorLocation());
        newSensor.setSensorStatus(sensor.getSensorStatus());
        newSensor.setUserEmail(sensor.getUserEmail());
        newSensor.setVendorEmail(sensor.getVendorEmail());

        sensorDAO.updateSensor(newSensor);
        return new ResponseEntity<Sensor>(newSensor,HttpStatus.OK);

    }

    @RequestMapping(value="/sensors/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Sensor> deleteUser(@PathVariable("id") int id){
        SensorDAO sensorDAO=new SensorDAO();
        Sensor delSensor=sensorDAO.findBySensorId(id);
        if(delSensor==null){
            return new ResponseEntity<Sensor>(HttpStatus.NOT_FOUND);
        }

        sensorDAO.deleteSensor(id);
        return new ResponseEntity<Sensor>(delSensor,HttpStatus.NO_CONTENT);

    }

}
