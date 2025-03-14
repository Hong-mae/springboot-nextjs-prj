import { createStore } from "zustand";
import { devtools } from "zustand/middleware";

type ArticleType = {
  id: string;
  subject: string;
  content: string;
  createdAt: string;
  modifiedAt: string;
};
export type ArticlesState = {
  articles: Array<ArticleType>;
  ids: Array<number>;
};

export type ArticlesActions = {
  upload: (newState: ArticlesState) => void;
};

export type ArticlesStore = ArticlesState & ArticlesActions;

export const defaultState: ArticlesState = {
  articles: [],
  ids: [],
};

export const createArticlesStore = (
  initState: ArticlesState = defaultState
) => {
  return createStore<ArticlesStore>()(
    devtools((set) => ({
      ...initState,
      upload: (newState: ArticlesState) => set((state) => newState),
    }))
  );
};
