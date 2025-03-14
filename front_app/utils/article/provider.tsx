"use client";

import { createContext, ReactNode, useContext, useRef } from "react";
import { ArticlesStore, createArticlesStore } from "./store";
import { useStore } from "zustand";

export type ArticlesStoreApi = ReturnType<typeof createArticlesStore>;

export const ArticlesStoreContext = createContext<ArticlesStoreApi | undefined>(
  undefined
);

export interface ArticleStoreProviderProps {
  children: ReactNode;
}

export const ArticlesStoreProvider = ({
  children,
}: ArticleStoreProviderProps) => {
  const storeRef = useRef<ArticlesStoreApi>(null);

  if (!storeRef.current) {
    storeRef.current = createArticlesStore();
  }

  return (
    <ArticlesStoreContext.Provider value={storeRef.current}>
      {children}
    </ArticlesStoreContext.Provider>
  );
};

export const useArticlesStore = <T,>(
  selector: (store: ArticlesStore) => T
): T => {
  const articlesStoreContext = useContext(ArticlesStoreContext);

  if (!articlesStoreContext) {
    throw new Error("useArticleStore must be used within ArticleStoreProvider");
  }

  return useStore(articlesStoreContext, selector);
};
