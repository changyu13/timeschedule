import { Button } from "@mantine/core";
import styles from "./ShowSubject.module.css";
interface Props {
  subNo: string;
  subName: string | undefined;
  professor: string | undefined;
  credit: number | undefined;
  onClickRequired: (subNo: string) => void;
  onClickElective: (subNo: string) => void;
}
const ShowSubject: React.FC<Props> = ({
  subNo,
  subName,
  professor,
  credit,
  onClickRequired,
  onClickElective,
}) => {
  return (
    <div>
      <div>과목 : {subName}</div>
      <div>교수 : {professor}</div>
      <div>학점 : {credit}</div>
      <Button
        variant="filled"
        color="black"
        className={styles.btn}
        onClick={() => onClickRequired(subNo)}
      >
        필수
      </Button>
      <Button
        variant="filled"
        color="gray"
        onClick={() => onClickElective(subNo)}
      >
        선택
      </Button>
    </div>
  );
};

export default ShowSubject;
