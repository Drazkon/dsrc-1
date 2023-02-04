package script.item.special;


import script.*;
import script.library.*;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;
import java.lang.String;
import script.base_script;


public class jewelry_gift extends script.base_script {

    public jewelry_gift()
    {
    }
    public static final String HEROIC_JEWELRY_SETS = "datatables/skill/character_builder_heroic_jewelry.iff";
    public static final String[] HEROIC_JEWELRY_LIST =
    {
        "Bounty Hunter Enforcer (DPS)",
        "Bounty Hunter Flawless (Utility A)",
        "Bounty Hunter Dire Fate (PvP)",
        "Medic Striker's (DPS)",
        "Medic First Responder's (Healing)",
        "Medic Blackbar's Doom (PvP)",
        "Jedi Duelist (Saber DPS)",
        "Jedi Dark Fury (Force Power DPS)",
        "Jedi Guardian (Tanking)",
        "Commando Grenadier (Grenade DPS)",
        "Commando Frontman (Tanking)",
        "Commando Juggernaut (Weapon DPS)",
        "Smuggler Scoundrel (DPS)",
        "Smuggler Rogue (PvP)",
        "Smuggler Gambler's (PvE)",
        "Spy Assassin's (DPS)",
        "Spy Ghost (PvP)",
        "Spy Razor Cat (DPS)",
        "Officer Dead Eye (DPS)",
        "Officer Hellstorm (AoE DPS)",
        "Officer General's (Group PvE)",
        "Heroism (Stats)",
        "Entertainer Tradegy",
        "Trader Tinkerer's"
    };
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
            closeOldWindow(player);
            handleHeroicJewelry(player);
        }
        return SCRIPT_CONTINUE;
    }

    public void handleHeroicJewelry(obj_id player) throws InterruptedException
    {
        refreshMenu(player, "Select the desired option", "Jewelry Gift Selection", HEROIC_JEWELRY_LIST, "handleHeroicJewelrySelect", false);
    }

    public int handleHeroicJewelrySelect(obj_id self, dictionary params) throws InterruptedException
    {
        obj_id giftItem = self;
        obj_id player = sui.getPlayerId(params);
        obj_id pInv = utils.getInventoryContainer(player);
        int btn = sui.getIntButtonPressed(params);
        int idx = sui.getListboxSelectedRow(params);
        if (btn == sui.BP_CANCEL)
        {
            cleanScriptVars(player);
            closeOldWindow(player);
            return SCRIPT_CONTINUE;
        }
        if (idx == -1 || idx > HEROIC_JEWELRY_LIST.length)
        {
            cleanScriptVars(player);
            return SCRIPT_CONTINUE;
        }
        if (!isIdValid(player))
        {
            sendSystemMessageTestingOnly(player, "The system is unable to complete the transaction.");
            cleanScriptVars(player);
            return SCRIPT_OVERRIDE;
        }
        if (!isIdValid(pInv))
        {
            sendSystemMessageTestingOnly(player, "The system is unable to complete the transaction.");
            cleanScriptVars(player);
            return SCRIPT_OVERRIDE;
        }
        if (getVolumeFree(pInv) <= 5)
        {
            sendSystemMessageTestingOnly(player, "Your Inventory is Full, please make room and try again.");
            cleanScriptVars(player);
            return SCRIPT_OVERRIDE;
        }
        String column = "";
        switch (idx)
        {
            case 0:
                column = "set_bh_dps";
                break;
            case 1:
                column = "set_bh_utility_a";
                break;
            case 2:
                column = "set_bh_utility_b";
                break;
            case 3:
                column = "set_medic_dps";
                break;
            case 4:
                column = "set_medic_utility_a";
                break;
            case 5:
                column = "set_medic_utility_b";
                break;
            case 6:
                column = "set_jedi_dps";
                break;
            case 7:
                column = "set_jedi_utility_a";
                break;
            case 8:
                column = "set_jedi_utility_b";
                break;
            case 9:
                column = "set_commando_dps";
                break;
            case 10:
                column = "set_commando_utility_a";
                break;
            case 11:
                column = "set_commando_utility_b";
                break;
            case 12:
                column = "set_smuggler_dps";
                break;
            case 13:
                column = "set_smuggler_utility_a";
                break;
            case 14:
                column = "set_smuggler_utility_b";
                break;
            case 15:
                column = "set_spy_dps";
                break;
            case 16:
                column = "set_spy_utility_a";
                break;
            case 17:
                column = "set_spy_utility_b";
                break;
            case 18:
                column = "set_officer_dps";
                break;
            case 19:
                column = "set_officer_utility_a";
                break;
            case 20:
                column = "set_officer_utility_b";
                break;
            case 21:
                column = "set_hero";
                break;
            case 22:
                column = "set_ent";
                break;
            case 23:
                column = "set_trader";
                break;
            default:
                cleanScriptVars(player);
                return SCRIPT_CONTINUE;
        }
        sendSystemMessageTestingOnly(player, "Selected Item!");
        if (column != null && !column.equals(""))
        {
            String[] itemSet = dataTableGetStringColumn(HEROIC_JEWELRY_SETS, column);
            if ((itemSet != null) && (itemSet.length != 0))
            {
                for (String s : itemSet) {
                    static_item.createNewItemFunction(s, pInv);
                }
            destroyObject(giftItem);
            }
        }
        return SCRIPT_CONTINUE;
    }
    public void cleanScriptVars(obj_id player) throws InterruptedException
    {
        obj_id self = getSelf();
        utils.removeScriptVarTree(player, "HEROIC_JEWELRY");
        utils.removeScriptVarTree(self, "HEROIC_JEWELRY");
        setObjVar(player, "HEROIC_JEWELRY", true);
    }
    public void closeOldWindow(obj_id player) throws InterruptedException
    {
        String playerPath = "HEROIC_JEWELRY.";
        if (utils.hasScriptVar(player, "HEROIC_JEWELRY.pid"))
        {
            int oldpid = utils.getIntScriptVar(player, "HEROIC_JEWELRY.pid");
            forceCloseSUIPage(oldpid);
            utils.removeScriptVar(player, "HEROIC_JEWELRY.pid");
        }
    }
    public void setWindowPid(obj_id player, int pid) throws InterruptedException
    {
        if (pid > -1)
        {
            utils.setScriptVar(player, "HEROIC_JEWELRY.pid", pid);
        }
    }
    public void refreshMenu(obj_id player, String prompt, String title, String[] options, String myHandler, boolean draw) throws InterruptedException
    {
        obj_id self = getSelf();
        closeOldWindow(player);
        if (draw == false)
        {
            int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL_REFRESH, title, options, myHandler, false, false);
            sui.listboxUseOtherButton(pid, "Back");
            sui.showSUIPage(pid);
            setWindowPid(player, pid);
        }
        else
        {
            int pid = sui.listbox(self, player, prompt, sui.OK_CANCEL, title, options, myHandler, true, false);
            sui.showSUIPage(pid);
            setWindowPid(player, pid);
        }
    }
}