package script.systems.species_change;

import script.library.species_change;
import script.library.*;
import script.*;

public class click_species_token extends script.base_script
{
    public click_species_token()
    {
    }
    public int OnInitialize(obj_id self) throws InterruptedException
    {
        if (!hasObjVar(self, "foundOwner"))
        {
            obj_id player = utils.getContainingPlayer(self);
            if (isIdValid(player) && isPlayer(player))
            {
                setObjVar(self, "foundOwner", player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnTransferred(obj_id self, obj_id sourceContainer, obj_id destContainer, obj_id transferer) throws InterruptedException
    {
        if (!hasObjVar(self, "foundOwner"))
        {
            obj_id player = utils.getContainingPlayer(self);
            if (isIdValid(player) && isPlayer(player))
            {
                setObjVar(self, "foundOwner", player);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuRequest(obj_id self, obj_id player, menu_info mi) throws InterruptedException
    {
        if (canManipulate(player, self, true, true, 15, true))
        {
            if (utils.isNestedWithinAPlayer(self))
            {
                menu_info_data mid = mi.getMenuItemByType(menu_info_types.ITEM_USE);
                if (mid != null)
                {
                    mid.setServerNotify(true);
                }
                else 
                {
                    mi.addRootMenu(menu_info_types.ITEM_USE, new string_id("ui_radial", "item_use"));
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    public int OnObjectMenuSelect(obj_id self, obj_id player, int item) throws InterruptedException
    {
        if (utils.getContainingPlayer(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {
            if (canManipulate(player, self, true, true, 15, true))
            {
                String template = getTemplateName(self);
                if (getCount(self) >= 0)
                {
                    species_change.startSpeciesChange(player);
                    static_item.decrementStaticItem(self);
                }
                else 
                {
                    destroyObject(self);
                }
            }
        }
        return SCRIPT_CONTINUE;
    }
    
}
