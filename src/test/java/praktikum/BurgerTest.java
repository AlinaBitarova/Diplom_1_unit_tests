package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BurgerTest {
    Burger burger;

    @Mock
    Bun bun;

    @Mock
    Ingredient ingredient1;

    @Mock
    Ingredient ingredient2;

    @Before
    public void setUp() {
        burger = new Burger();
    }

    @Test
    public void setBunsTest() {
        burger.setBuns(bun);
        assertEquals("Булка добавлена", bun, burger.bun);
    }

    @Test
    public void addIngredientTest() {
        burger.addIngredient(ingredient1);
        assertTrue(burger.ingredients.contains(ingredient1));
    }

    @Test
    public void removeIngredientTest() {
        burger.addIngredient(ingredient1);
        burger.removeIngredient(0);
        assertFalse(burger.ingredients.contains(ingredient1));
    }

    @Test
    public void moveIngredientTest() {
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.moveIngredient(0, 1);
        assertEquals("Ингредиент не переместился", ingredient2, burger.ingredients.get(0));
        assertEquals("Ингредиент не переместился", ingredient1, burger.ingredients.get(1));
    }

    @Test
    public void getPriceTest() {
        Mockito.when(bun.getPrice()).thenReturn(200.0f);
        Mockito.when(ingredient1.getPrice()).thenReturn(300.0f);
        Mockito.when(ingredient2.getPrice()).thenReturn(100.0f);
        burger.setBuns(bun);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        float expectedBurgerPrice = 800.0f;
        assertEquals("Стоимость неверная", expectedBurgerPrice, burger.getPrice(), 0.001f);
    }

    @Test
    public void getReceiptTest() {
        Mockito.when(bun.getName()).thenReturn("Краторная булка");
        Mockito.when(bun.getPrice()).thenReturn(200.0f);
        Mockito.when(ingredient1.getType()).thenReturn(IngredientType.FILLING);
        Mockito.when(ingredient1.getName()).thenReturn("Хрустящие минеральные кольца");
        Mockito.when(ingredient1.getPrice()).thenReturn(300.0f);
        burger.setBuns(bun);
        burger.addIngredient(ingredient1);
        String ecpectedReceipt = String.format(
                "(==== %s ====)%n" +
                "= %s %s =%n" +
                "(==== %s ====)%n" +
                "%nPrice: %f%n",
                "Краторная булка",
                "filling", "Хрустящие минеральные кольца",
                "Краторная булка",
                700.0f
        );
        assertEquals("Чек неверный", ecpectedReceipt, burger.getReceipt());
    }
}