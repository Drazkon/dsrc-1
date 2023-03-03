package script.library;

import script.*;

import java.util.Enumeration;

public class species_change extends script.base_script
{
    public species_change()
    {
    }

    public static final String OBJVAR_SPCHANGE_ROOT = "species_change";

    public static void startClickSpeciesChange(obj_id player) throws InterruptedException
    {
        if (hasObjVar(player, "species_change"))
        {
            removeObjVar(player, "species_changec");
        }
        if (hasScript(player, "systems.species_change.click_species_change"))
        {
            playUiEffect(player, "showMediator=speciesselection");
        }
        else 
        {
            attachScript(player, "species_change.click_species_change");
        }
    }

    public static void startSpeciesChange(obj_id player) throws InterruptedException
    {
        if (hasScript(player, "systems.species_change.click_species_change"))
        {
            playUiEffect(player, "showMediator=speciesselection");
        }
        else 
        {
            attachScript(player, "systems.species_change.click_species_change");
        }
    }
    
    public static void handleSpeciesChange(obj_id player) throws InterruptedException
    {
        removeObjVar(player, "species_change");
        if (hasScript(player, "systems.species_change.click_species_change"))
        {
            detachScript(player, "systems.species_change.click_species_change");
        }
    }
}
