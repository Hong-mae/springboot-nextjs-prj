import { revalidatePath } from "next/cache";
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

  return list;
};

const ArticleForm = () => {
  const handleSubmit = async (formData: FormData) => {
    "use server";

    const rawData = {
      subject: formData.get("subject"),
      content: formData.get("content"),
    };

    const response = await fetch("http://localhost:8090/api/v1/articles", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(rawData),
    });

    if (!response.ok) {
      return "처리되지 않았습니다.";
    }

    return "";
  };

  return (
    <>
      <h3>등록 Form</h3>
      <form action={handleSubmit}>
        <input type="text" name="subject" placeholder="제목" />
        <input type="text" name="content" placeholder="내용" />
        <button type="submit">등록</button>

        <output />
      </form>
    </>
  );
};

export default async function Article() {
  const {
    resultCode,
    data: { articles },
  }: ArticlesType = await getArticles();

  if (resultCode.startsWith("F-")) return <p>오류 발생</p>;

  return (
    <>
      <ArticleForm />
      <ul>
        {articles.map((e) => (
          <li key={e.id}>
            {e.id} / <Link href={`/article/${e.id}`}>{e.subject}</Link>/
            {e.createdAt}
          </li>
        ))}
      </ul>
    </>
  );
}
