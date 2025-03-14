import { ArticlesStoreProvider } from "../../../utils/article/provider";

export default function ArticleLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <>
      <ArticlesStoreProvider>{children}</ArticlesStoreProvider>
    </>
  );
}
