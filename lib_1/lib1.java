package lib_1;
/**
 * @author Половникова Алиса 3312
 * @version 1.0
 */

public class lib1 {
	/**
	 * Точка входа в программу, которая выполняет сортировку заданного массива по возрастанию
	 * @param args 
	 * аргументов нет
	 */

	public static void main(String[] args) {
		//Создаем и инициализируем новый массив
				int[] temp = new int[] {1,3,9,5,7,2,5};
				int len = temp.length;
				
				System.out.println("Array before sorting:");
				//Выводим начальный массив
				for(int i = 0; i < len; i++) {
					System.out.print(temp[i] + " ");
				}
				
				int v;//переменная для временного хранения элемента массива при сортировке
				// Сортировка
				for(int j = 0; j < len - 1; j++) 
				{
					for(int i = 0; i < len - 1; i++) 
					{
						if(temp[i] > temp[i+1]) 
						{
							v = temp[i];
							temp[i] = temp[i+1];
							temp[i+1] = v;
						}
					}
				}
				System.out.println("\nResult:");
				// Вывод конечного массива
				for(int i = 0; i < len; i++) {
					System.out.print(temp[i] + " ");
				}
	}
}


