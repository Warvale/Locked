package net.warvale.prison.npcs.merchants;

import net.warvale.prison.Prison;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("all")
public class MerchantManager {
    private static Prison plugin = Prison.get();

    public static void createNPC(String name, Player creator) {
        Location location = creator.getLocation();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        String world = location.getWorld().getName();
        try {
            File file = plugin.getPath().getAbsoluteFile();
            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(new FileReader(file.getPath()));
            JSONObject jsonObject = (JSONObject) parsed;
            JSONArray npcsArray = (JSONArray) jsonObject.get("npcs");

            JSONObject locationObject = new JSONObject();
            locationObject.put("x", x);
            locationObject.put("y", y);
            locationObject.put("z", z);
            locationObject.put("world", world);

            JSONArray loreArray = new JSONArray();
            JSONObject loreLineObject = new JSONObject();
            loreLineObject.put("line", "Example");
            loreArray.add(loreLineObject);

            JSONArray enchantArray = new JSONArray();
            JSONObject enchantObject = new JSONObject();
            enchantObject.put("enchant", "sharpness");
            enchantObject.put("level", 1);
            enchantArray.add(enchantObject);

            JSONObject itemObject = new JSONObject();
            itemObject.put("itemname", "example");
            itemObject.put("material", "coal");
            itemObject.put("lore", loreArray);
            itemObject.put("enchants", enchantArray);

            JSONObject npcObject = new JSONObject();
            npcObject.put("location", locationObject);
            npcObject.put("price", 10);
            npcObject.put("item", itemObject);
            npcObject.put("denial", "You can not afford this item!");
            npcObject.put("success", "Successfully purchased item!");
            npcObject.put("colorcode", "&f");

            JSONObject mainObject = new JSONObject();
            mainObject.put("name", name);
            mainObject.put(name, npcObject);

            npcsArray.add(mainObject);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteNPC(String name) {
        try {
            File file = plugin.getPath().getAbsoluteFile();
            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(new FileReader(file.getPath()));
            JSONObject jsonObject = (JSONObject) parsed;
            JSONArray npcsArray = (JSONArray) jsonObject.get("npcs");
            JSONObject npcObject = (JSONObject) jsonObject.get("");
            for (int i = 0; i < npcsArray.size(); i++) {
                JSONObject a = (JSONObject) npcsArray.get(i);
                if (a.get("name") == name) {
                    npcsArray.remove(a);
                    break;
                }
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void renameNPC(String oldName, String newName) {
        try {
            File file = plugin.getPath().getAbsoluteFile();
            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(new FileReader(file.getPath()));
            JSONObject jsonObject = (JSONObject) parsed;
            JSONArray npcsArray = (JSONArray) jsonObject.get("npcs");
            JSONObject npcObject = (JSONObject) jsonObject.get("");
            for (int i = 0; i < npcsArray.size(); i++) {
                JSONObject a = (JSONObject) npcsArray.get(i);
                if (a.get("name") == oldName) {
                    npcObject = (JSONObject) a.get(oldName);
                    npcsArray.remove(a);
                    break;
                }
            }
            JSONObject mainObject = new JSONObject();
            mainObject.put("name", newName);
            mainObject.put(newName, npcObject);

            npcsArray.add(mainObject);


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void setLocation(Location location, String npc) {
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        String world = location.getWorld().getName();
        try {
            File file = plugin.getPath().getAbsoluteFile();
            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(new FileReader(file.getPath()));
            JSONObject jsonObject = (JSONObject) parsed;
            JSONArray npcsArray = (JSONArray) jsonObject.get("npcs");
            JSONObject npcObject = (JSONObject) jsonObject.get("");
            for (Object a : npcsArray) {
                JSONObject aa = (JSONObject) a;
                if (aa.get("name") == npc) {
                    npcObject = (JSONObject) aa.get(npc);
                    break;
                }
            }
            JSONObject locationObject = (JSONObject) npcObject.get("location");
            locationObject.put("x", x);
            locationObject.put("y", y);
            locationObject.put("z", z);
            locationObject.put("world", world);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void setPrice(String npc, int price) {
        try {
            File file = plugin.getPath().getAbsoluteFile();
            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(new FileReader(file.getPath()));
            JSONObject jsonObject = (JSONObject) parsed;
            JSONArray npcsArray = (JSONArray) jsonObject.get("npcs");
            JSONObject npcObject = (JSONObject) jsonObject.get("");
            for (Object a : npcsArray) {
                JSONObject aa = (JSONObject) a;
                if (aa.get("name") == npc) {
                    npcObject = (JSONObject) aa.get(npc);
                    break;
                }
            }
            npcObject.put("price", price);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void setItem(String npc, ItemStack item) {
        try {
            File file = plugin.getPath().getAbsoluteFile();
            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(new FileReader(file.getPath()));
            JSONObject jsonObject = (JSONObject) parsed;
            JSONArray npcsArray = (JSONArray) jsonObject.get("npcs");
            JSONObject npcObject = (JSONObject) jsonObject.get("");
            for (Object a : npcsArray) {
                JSONObject aa = (JSONObject) a;
                if (aa.get("name") == npc) {
                    npcObject = (JSONObject) aa.get(npc);
                    break;
                }
            }
            JSONObject itemObject = (JSONObject) npcObject.get("item");

            ItemMeta meta = item.getItemMeta();
            String itemName = meta.getDisplayName();
            ArrayList<String> lore = (ArrayList<String>) meta.getLore();
            HashMap<Enchantment, Integer> enchants = (HashMap<Enchantment, Integer>) meta.getEnchants();

            JSONArray loreArray = (JSONArray) itemObject.get("lore");
            JSONObject jo = new JSONObject();
            loreArray.clear();
            for (int i = 0; i < lore.size(); i++) {
                jo.put("lore", lore.get(i));
                loreArray.add(jo);
            }
            JSONArray enchantsArray = (JSONArray) itemObject.get("enchants");
            JSONObject eo = new JSONObject();
            enchantsArray.clear();
            for (int i = 0; i < enchants.size(); i++) {
                eo.put("enchant", enchants.keySet().toArray()[i].toString());
                eo.put("level", enchants.get(enchants.keySet().toArray()[i]));
                enchantsArray.add(eo);
            }

            itemObject.put("itemname", itemName);

            itemObject.put("material", item.getType().toString().toLowerCase());

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void setDenialMessage(String npc, String message) {
        try {
            File file = plugin.getPath().getAbsoluteFile();
            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(new FileReader(file.getPath()));
            JSONObject jsonObject = (JSONObject) parsed;
            JSONArray npcsArray = (JSONArray) jsonObject.get("npcs");
            JSONObject npcObject = (JSONObject) jsonObject.get("");
            for (Object a : npcsArray) {
                JSONObject aa = (JSONObject) a;
                if (aa.get("name") == npc) {
                    npcObject = (JSONObject) aa.get(npc);
                    break;
                }
            }

            npcObject.put("denial", message);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void setSuccessMessage(String npc, String message) {
        try {
            File file = plugin.getPath().getAbsoluteFile();
            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(new FileReader(file.getPath()));
            JSONObject jsonObject = (JSONObject) parsed;
            JSONArray npcsArray = (JSONArray) jsonObject.get("npcs");
            JSONObject npcObject = (JSONObject) jsonObject.get("");
            for (Object a : npcsArray) {
                JSONObject aa = (JSONObject) a;
                if (aa.get("name") == npc) {
                    npcObject = (JSONObject) aa.get(npc);
                    break;
                }
            }

            npcObject.put("success", message);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void setNameColor(String npc, String colorCode) {
        try {
            File file = plugin.getPath().getAbsoluteFile();
            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(new FileReader(file.getPath()));
            JSONObject jsonObject = (JSONObject) parsed;
            JSONArray npcsArray = (JSONArray) jsonObject.get("npcs");
            JSONObject npcObject = (JSONObject) jsonObject.get("");
            for (Object a : npcsArray) {
                JSONObject aa = (JSONObject) a;
                if (aa.get("name") == npc) {
                    npcObject = (JSONObject) aa.get(npc);
                    break;
                }
            }

            npcObject.put("colorcode", colorCode);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static Location getLocation(String npc) {
        double x = 0;
        double y = 0;
        double z = 0;
        String world = "world";
        try {
            File file = plugin.getPath().getAbsoluteFile();
            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(new FileReader(file.getPath()));
            JSONObject jsonObject = (JSONObject) parsed;
            JSONArray npcsArray = (JSONArray) jsonObject.get("npcs");
            JSONObject npcObject = (JSONObject) jsonObject.get("");
            for (Object a : npcsArray) {
                JSONObject aa = (JSONObject) a;
                if (aa.get("name") == npc) {
                    npcObject = (JSONObject) aa.get(npc);
                    break;
                }
            }
            JSONObject locationObject = (JSONObject) npcObject.get("location");
            x = (double) locationObject.get("x");
            y = (double) locationObject.get("y");
            z = (double) locationObject.get("z");
            world = (String) locationObject.get("world");

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return new Location(Bukkit.getWorld(world), x, y, z);
    }

    public static int getPrice(String npc) {
        try {
            File file = plugin.getPath().getAbsoluteFile();
            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(new FileReader(file.getPath()));
            JSONObject jsonObject = (JSONObject) parsed;
            JSONArray npcsArray = (JSONArray) jsonObject.get("npcs");
            JSONObject npcObject = (JSONObject) jsonObject.get("");
            for (Object a : npcsArray) {
                JSONObject aa = (JSONObject) a;
                if (aa.get("name") == npc) {
                    npcObject = (JSONObject) aa.get(npc);
                    break;
                }
            }

            return (int) npcObject.get("price");

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return 10;
    }

    public static ItemStack getItem(String npc) {
        try {
            File file = plugin.getPath().getAbsoluteFile();
            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(new FileReader(file.getPath()));
            JSONObject jsonObject = (JSONObject) parsed;
            JSONArray npcsArray = (JSONArray) jsonObject.get("npcs");
            JSONObject npcObject = (JSONObject) jsonObject.get("");
            for (Object a : npcsArray) {
                JSONObject aa = (JSONObject) a;
                if (aa.get("name") == npc) {
                    npcObject = (JSONObject) aa.get(npc);
                    break;
                }
            }
            JSONObject itemObject = (JSONObject) npcObject.get("item");
            String itemName = (String) itemObject.get("itemname");
            ItemStack itemStack = new ItemStack(Material.valueOf(((String) itemObject.get("material")).toUpperCase()));
            ItemMeta meta = itemStack.getItemMeta();
            ArrayList<String> lore = (ArrayList<String>) meta.getLore();
            JSONArray loreArray = (JSONArray) itemObject.get("lore");

            for (Object loreLines : loreArray) {
                JSONObject loreData = (JSONObject) loreLines;
                String loreL = (String) loreData.get("line");
                lore.add(loreL);

            }
            meta.setLore(lore);
            meta.setDisplayName(itemName);
            JSONArray enchantsArray = (JSONArray) itemObject.get("enchants");

            for (Object enchantLines : enchantsArray) {
                JSONObject enchantData = (JSONObject) enchantLines;
                meta.addEnchant(Enchantment.getByName(((String) enchantData.get("enchant")).toUpperCase()), (int) enchantData.get("level"), true);
            }
            itemStack.setItemMeta(meta);
            return itemStack;

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return null;


    }

    public static String getDenialMessage(String npc) {
        try {
            File file = plugin.getPath().getAbsoluteFile();
            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(new FileReader(file.getPath()));
            JSONObject jsonObject = (JSONObject) parsed;
            JSONArray npcsArray = (JSONArray) jsonObject.get("npcs");
            JSONObject npcObject = (JSONObject) jsonObject.get("");
            for (Object a : npcsArray) {
                JSONObject aa = (JSONObject) a;
                if (aa.get("name") == npc) {
                    npcObject = (JSONObject) aa.get(npc);
                    break;
                }
            }

            return (String) npcObject.get("denial");

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSuccessMessage(String npc) {
        try {
            File file = plugin.getPath().getAbsoluteFile();
            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(new FileReader(file.getPath()));
            JSONObject jsonObject = (JSONObject) parsed;
            JSONArray npcsArray = (JSONArray) jsonObject.get("npcs");
            JSONObject npcObject = (JSONObject) jsonObject.get("");
            for (Object a : npcsArray) {
                JSONObject aa = (JSONObject) a;
                if (aa.get("name") == npc) {
                    npcObject = (JSONObject) aa.get(npc);
                    break;
                }
            }

            return (String) npcObject.get("success");

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getNameColor(String npc) {
        try {
            File file = plugin.getPath().getAbsoluteFile();
            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(new FileReader(file.getPath()));
            JSONObject jsonObject = (JSONObject) parsed;
            JSONArray npcsArray = (JSONArray) jsonObject.get("npcs");
            JSONObject npcObject = (JSONObject) jsonObject.get("");
            for (Object a : npcsArray) {
                JSONObject aa = (JSONObject) a;
                if (aa.get("name") == npc) {
                    npcObject = (JSONObject) aa.get(npc);
                    break;
                }
            }

            return (String) npcObject.get("colorcode");

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return "&f";
    }

    public static ArrayList<String> getAllNPCs() {
        ArrayList<String> a = new ArrayList<>();
        try {
            File file = plugin.getPath().getAbsoluteFile();
            JSONParser parser = new JSONParser();
            Object parsed = parser.parse(new FileReader(file.getPath()));
            JSONObject jsonObject = (JSONObject) parsed;
            JSONArray npcsArray = (JSONArray) jsonObject.get("npcs");
            for (Object npc : npcsArray) {
                a.add((String) ((JSONObject) npc).get("name"));

            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return a;
    }

    public static ArrayList<Location> getAllNPCsLocations() {
        ArrayList<Location> locations = new ArrayList<>();
        for (String npc : getAllNPCs()) {
            locations.add(getLocation(npc));
        }
        return locations;
    }
}
