import Link from "next/link";

type ArticlesType = {
  resultCode: string;
  msg: string;
  data: {
    articles: ReadonlyArray<{
      id: string;
      subject: string;
      content: string;
      createdAt: string;
      modifiedAt: string;
    }>;
  };
};

const getArticles = async (): Promise<ArticlesType> => {
  const list = await fetch("http://localhost:8090/api/v1/articles").then(
    (data) => data.json()
  );

  console.log(list);

  return list;
};

export default async function Article() {
  const {
    resultCode,
    msg,
    data: { articles },
  }: ArticlesType = await getArticles();

  if (resultCode.startsWith("F-")) return <p>오류 발생</p>;

  return (
    <>
      <div>{msg}</div>
      <ul>
        {articles.map((e) => (
          <li key={e.subject}>
            <Link href={`/article/${e.id}`}>{e.subject}</Link>
          </li>
        ))}
      </ul>
    </>
  );
}
