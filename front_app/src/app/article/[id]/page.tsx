"use client";

import { ParamValue } from "next/dist/server/request/params";
import { useParams } from "next/navigation";

interface Props {
  id: ParamValue;
}

type ArticleType = {
  id: string;
  subject: string;
  content: string;
  createdAt: string;
  modifiedAt: string;
};

const getArticle = async ({ id }: Props): Promise<ArticleType> => {
  const info = await fetch(`http://localhost:8090/api/v1/articles/${id}`).then(
    (data) => data.json()
  );

  return info;
};

export default async function ArticleDetail() {
  const params = useParams();
  const article = await getArticle(params.id);

  return <>게시판 상세 {params.id}번</>;
}
