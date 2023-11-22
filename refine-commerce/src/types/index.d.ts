export interface IProduct {
  id: string;
  title: string;
  price: number;
  category: string;
  description: string;
  image: string;
}

export interface ICartItem extends IProduct {
amount: number
}  