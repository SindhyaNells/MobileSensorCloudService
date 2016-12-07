package com.sensor.service.controller;

import com.sensor.service.domain.*;
import com.sensor.service.mapper.SensorDataMapper;
import com.sensor.service.model.db.plan.Plan;
import com.sensor.service.model.db.sensor.physical.Sensor;
import com.sensor.service.model.service.GetBillingInfo;
import com.sensor.service.model.service.GetSensorDataResponse;
import com.sensor.service.model.service.GetSensorDataTimeRange;
import com.sensor.service.model.service.GetVirtualSensorDataResponse;
import com.sensor.service.model.service.SensorDataRequest;
import com.sensor.service.model.db.sensor.virtual.VirtualSensor;
import com.sensor.service.model.db.sensor.virtual.VirtualSensorGroup;
import com.sensor.service.model.db.users.Users;
import com.sensor.service.model.db.vendor.Vendor;
import com.sensor.service.model.service.SetVirualSensorStatusRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value="/virtualsensor",method=RequestMethod.POST)
    public ResponseEntity<VirtualSensor> createVirtualSensor(@RequestBody VirtualSensor virtualSensor){

        VirtualSensorDAO virtualSensorDAO=new VirtualSensorDAO();
        virtualSensorDAO.createSensor(virtualSensor);
        return new ResponseEntity<VirtualSensor>(virtualSensor,HttpStatus.CREATED);

    }

    @RequestMapping(value = "/virtualsensor",method=RequestMethod.GET)
    public List virtualsensors(@RequestParam(value = "user_email", required = false,defaultValue = "0") String user_email){
        VirtualSensorDAO virtualsensorDAO=new VirtualSensorDAO();
        List<Sensor> sensorList=virtualsensorDAO.virtualSensorList(user_email);
        return sensorList;
    }

    @RequestMapping(value="/virtualSensorGroup",method=RequestMethod.POST)
    public ResponseEntity<VirtualSensorGroup> createVirtualSensor(@RequestBody VirtualSensorGroup virtualSensorGrp){

        VirtualSensorGroupDAO virtualSensorGrpDAO=new VirtualSensorGroupDAO();
        virtualSensorGrpDAO.createSensorGroup(virtualSensorGrp);
        return new ResponseEntity<VirtualSensorGroup>(virtualSensorGrp,HttpStatus.CREATED);

    }

    @RequestMapping(value="/virtualSensorGroup/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<VirtualSensorGroup> deleteVirtualSensorGroup(@PathVariable("id") int id){
        VirtualSensorGroupDAO virtualSensorGroupDAO=new VirtualSensorGroupDAO();
        virtualSensorGroupDAO.deleteVirtualSensorGroup(id);
        VirtualSensorGroup virtualSensorGrp=virtualSensorGroupDAO.findByVirtualSensorGrpId(id);
        if(virtualSensorGrp==null){
            return new ResponseEntity<VirtualSensorGroup>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<VirtualSensorGroup>(virtualSensorGrp,HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/virtualSensorGroup",method=RequestMethod.GET)
    public List virtualsensorsgroup(
        @RequestParam(value = "user_email", required = false,defaultValue = "0") String user_email){
        VirtualSensorGroupDAO virtualsensorDAO=new VirtualSensorGroupDAO();
        List<VirtualSensorGroup> grpList=virtualsensorDAO.virtualSensorGroupList(user_email);
        return grpList;
    }


    @RequestMapping(value="/users",method = RequestMethod.PUT)
    public ResponseEntity<Users> updateUser(@RequestBody Users users){
        UserDAO userDAO=new UserDAO();
        Users newUser=new Users();

        newUser.setUserEmail(users.getUserEmail());
        newUser.setUserName(users.getUserName());
        newUser.setUserPassword(users.getUserPassword());
        newUser.setPlanId(users.getPlanId());

        userDAO.updateUser(newUser);
        return new ResponseEntity<Users>(newUser,HttpStatus.OK);
    }

    @RequestMapping(value="/sensors/{id}",method=RequestMethod.PUT)
    public ResponseEntity<Sensor> updateSensor(@PathVariable("id") int id,
                                               @RequestBody Sensor sensor){

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

    @RequestMapping(value = "/virtualsensor/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<VirtualSensor> deleteVirtualSensor(@PathVariable("id") int id){
        VirtualSensorDAO virtualSensorDAO=new VirtualSensorDAO();
        VirtualSensor virtualSensor=virtualSensorDAO.findByVirtualSensorId(id);
        if(virtualSensor==null){
            return new ResponseEntity<VirtualSensor>(HttpStatus.NOT_FOUND);
        }

        virtualSensorDAO.deleteVirtualSensor(id);
        return new ResponseEntity<VirtualSensor>(virtualSensor,HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value="/sensor_data/{id}", method=RequestMethod.POST)
    public ResponseEntity<GetSensorDataResponse> getSensorData(@PathVariable("id") int id,
                                                               @RequestBody GetSensorDataTimeRange timeRange) {

        GetSensorDataResponse ssr = SensorDataMapper.getSensorDataForPhysicalSensor(timeRange, id);

        if (ssr==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ssr, HttpStatus.OK);
    }

    @RequestMapping(value="/sensor_data", method=RequestMethod.PUT)
    public void putSensorData(@RequestBody SensorDataRequest sensorDataRequest){
        SensorDataDAO sd = new SensorDataDAO();
        sd.persistSensorData(sensorDataRequest);
    }

    @RequestMapping(value="/virtual_sensor_group_data/{id}", method = RequestMethod.POST)
    public ResponseEntity<GetVirtualSensorDataResponse> getVirtualSensorData(@PathVariable("id") int group_id,
                                                                             @RequestBody GetSensorDataTimeRange timeRange){

        VirtualSensorGroupDAO virtualSensorGroupDAO=new VirtualSensorGroupDAO();
        VirtualSensorGroup virtualSensorGroup=virtualSensorGroupDAO.findByVirtualSensorGrpId(group_id);
        List<Integer> virtualSensorList=virtualSensorGroup.getVirtualSensorId();


        GetVirtualSensorDataResponse getVirtualSensorDataResponse=new GetVirtualSensorDataResponse();

        List<GetSensorDataResponse> sensorDataResponseArrayList=new ArrayList<>();

        for(int i=0;i<virtualSensorList.size();i++){
            int virtual_sensor_id=virtualSensorList.get(i);
            VirtualSensorDAO virtualSensorDAO=new VirtualSensorDAO();
            int physical_sensor_id = virtualSensorDAO.getPhysicalSensorID(virtual_sensor_id);
            sensorDataResponseArrayList.add(SensorDataMapper.getSensorDataForPhysicalSensor(timeRange,physical_sensor_id));
        }
        getVirtualSensorDataResponse.setAllSensorData(sensorDataResponseArrayList);

        return new ResponseEntity<>(getVirtualSensorDataResponse,HttpStatus.OK);

    }

    
    
    
    @RequestMapping(value = "/sensor_status/{status}", method = RequestMethod.POST) 
    public ResponseEntity<String> setVirtualSensorStatus(@PathVariable("status")String status, 
                                                                         @RequestBody SetVirualSensorStatusRequest sensorStatusRequest) {
        VirtualSensorDAO virtualSensorDAO = new VirtualSensorDAO();
        String result = virtualSensorDAO.startStopVirtualSensor(sensorStatusRequest.getSensorId(),
            sensorStatusRequest.getUserId(),status);
        if (result==null || result.isEmpty())
            return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
        
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @RequestMapping(value = "/get_billing/{user_email}", method = RequestMethod.GET)
    public ResponseEntity<GetBillingInfo> getBillingInfo(@PathVariable("user_email") String userEmail) {
        GetBillingInfo bill = new GetBillingInfo();
        VirtualSensorDAO vsDAO = new VirtualSensorDAO();
        Map<Integer,Double> billMap =  vsDAO.getVirtualSensorBillingInfo(userEmail);
        if (billMap.size() <=0)
            return new ResponseEntity<>(bill,HttpStatus.NOT_FOUND);
        bill.setAmountMap(billMap);
        return new ResponseEntity<>(bill,HttpStatus.OK);
    }
}
