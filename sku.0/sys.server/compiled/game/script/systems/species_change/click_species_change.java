package script.systems.species_change;

import script.obj_id;

public class click_species_change extends script.base_script
{
    public click_species_change()
    {
    }
    public int OnAttach(obj_id self) throws InterruptedException
    {
        playUiEffect(self, "showMediator=SpeciesSelection");
        return SCRIPT_CONTINUE;
    }
    public int OnRemovingFromWorld(obj_id self) throws InterruptedException
    {
        detachScript(self, "systems.species_change.click_species_change");
        return SCRIPT_CONTINUE;
    }
}
