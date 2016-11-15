package soselab.easyelarn.controller;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import soselab.easyelarn.client.NoteClient;
import soselab.easyelarn.client.PackClient;

@RestController
public class WebBackController {
    @Autowired
    private NoteClient noteClient;
    @Autowired
    private PackClient packClient;

    @RequestMapping(method = RequestMethod.POST, path = "/note")
    public void note(@RequestBody String request) {
        try {
            JSONObject jsonObject = new JSONObject(request);
            JSONObject note = jsonObject.getJSONObject("note");
            jsonObject.remove("note");
            System.out.println(request);
            noteClient.addNote(note.toString());
            packClient.updateVersion(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
