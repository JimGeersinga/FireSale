export interface Auction {
    id: number;
    name: string;
    description: string,
    startDate: Date,
    endDate: Date,
    categories: string[],
    tags: string[],
    images: string[],
    bids: string[],
  }