package script.item.special;


import script.*;
import script.library.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;
import java.lang.String;
import script.base_script;


public class flag_instance extends script.base_script {

    public flag_instance()
    {
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
            //check for instance_name objvar
            String instanceName = getStaticItemName(self);
            //check for instance_name objvar
            if (instanceName == null || instanceName.equals(""))
            {
                CustomerServiceLog("item", "flag_instance: " + self + " Name: " + getName(self) + " had an invalid static item name. Item object is bailing out early as a result.");
                sendSystemMessageTestingOnly(player, "Static Name not found!.");
                return SCRIPT_CONTINUE;
            }
            //grab list of instances
            String[] instanceFlags = dataTableGetStringColumn(instance.INSTANCE_DATATABLE, "key_required");
            //check to to see if instance name is in array of instances
            boolean contains = false;
            for(String x : instanceFlags)
            {
                if(x.equals(instanceName))
                {
                    contains = true;
                    break;
                }
            }
            if (contains)
            {
                instance.flagPlayerForInstance(player, instanceName);
                sendSystemMessageTestingOnly(player, "Heroic Instance Flagged");
                static_item.decrementStaticItem(self);
            }
            else
            {
                sendSystemMessageTestingOnly(player, "No Instance Flags Found.");
            }
        }
        return SCRIPT_CONTINUE;
    }
}