package script.item.special;


import script.*;
import script.library.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;
import java.lang.String;
import script.base_script;


public class heroic_gift extends script.base_script {

    public heroic_gift()
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
        obj_id giftItem = self;
        if (utils.getContainingPlayer(self) != player)
        {
            return SCRIPT_CONTINUE;
        }
        if (item == menu_info_types.ITEM_USE)
        {

            obj_id pInv = utils.getInventoryContainer(player);
            static_item.createNewItemFunction("heroic_axkva_min", pInv);
            static_item.createNewItemFunction("heroic_ig88", pInv);
            static_item.createNewItemFunction("heroic_tusken_army", pInv);
            static_item.createNewItemFunction("heroic_star_destroyer", pInv);
            static_item.createNewItemFunction("heroic_exar_kun", pInv);
            static_item.createNewItemFunction("uplink_cave", pInv);
            static_item.createNewItemFunction("decrepit_droid_factory", pInv);
            static_item.createNewItemFunction("working_droid_factory", pInv);
            static_item.createNewItemFunction("mustafar_droid_army", pInv);
            static_item.createNewItemFunction("mustafar_volcano", pInv);
            static_item.createNewItemFunction("sher_kar_cave", pInv);
            destroyObject(giftItem);
        }
        return SCRIPT_CONTINUE;
    }
}